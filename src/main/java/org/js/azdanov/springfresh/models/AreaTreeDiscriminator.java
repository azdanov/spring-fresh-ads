package org.js.azdanov.springfresh.models;

import java.util.Collections;
import pl.exsio.nestedj.config.jpa.discriminator.MapJpaTreeDiscriminator;

public class AreaTreeDiscriminator extends MapJpaTreeDiscriminator<Integer, Area> {

  private static final ThreadLocal<String> AREA_DISCRIMINATOR = new ThreadLocal<>();

  public static void setAreaDiscriminator(String areaDiscriminator) {
    AREA_DISCRIMINATOR.set(areaDiscriminator);
  }

  public static void cleanupDiscriminator() {
    AREA_DISCRIMINATOR.remove();
  }

  public AreaTreeDiscriminator() {
    super(Collections.singletonMap("discriminator", AREA_DISCRIMINATOR::get));
  }
}
