package org.js.azdanov.springfresh.loader;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.slugify.Slugify;
import java.io.IOException;
import java.util.List;
import org.js.azdanov.springfresh.dtos.AreaTreeDTO;
import org.js.azdanov.springfresh.models.Area;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ResourceUtils;
import pl.exsio.nestedj.NestedNodeRepository;

@Component
@ConditionalOnProperty(value = "dataloader.enabled", havingValue = "true")
@Transactional
public class DataLoader implements CommandLineRunner {
  private final Slugify slugify;
  private final NestedNodeRepository<Integer, Area> areaNestedNodeRepository;

  public DataLoader(Slugify slugify, NestedNodeRepository<Integer, Area> areaNestedNodeRepository) {
    this.slugify = slugify;
    this.areaNestedNodeRepository = areaNestedNodeRepository;
  }

  @Override
  public void run(String... args) throws Exception {
    loadAreas();
  }

  private void loadAreas() throws IOException {
    ObjectMapper mapper = new ObjectMapper();

    List<AreaTreeDTO> countries =
        mapper.readValue(
            ResourceUtils.getFile("classpath:areas.json"),
            mapper.getTypeFactory().constructCollectionType(List.class, AreaTreeDTO.class));

    countries.forEach(
        countryDTO -> {
          String countrySlug = this.slugify.slugify(countryDTO.name());
          Area country = new Area(countryDTO.name(), countrySlug);
          areaNestedNodeRepository.insertAsLastRoot(country);

          countryDTO
              .children()
              .forEach(
                  stateDTO -> {
                    String stateSlug =
                        this.slugify.slugify(countryDTO.name() + " " + stateDTO.name());
                    Area state = new Area(stateDTO.name(), stateSlug);
                    areaNestedNodeRepository.insertAsLastChildOf(state, country);

                    stateDTO
                        .children()
                        .forEach(
                            cityDTO -> {
                              String citySlug =
                                  this.slugify.slugify(stateDTO.name() + " " + cityDTO.name());
                              Area city = new Area(cityDTO.name(), citySlug);
                              areaNestedNodeRepository.insertAsLastChildOf(city, state);
                            });
                  });
        });
  }
}
