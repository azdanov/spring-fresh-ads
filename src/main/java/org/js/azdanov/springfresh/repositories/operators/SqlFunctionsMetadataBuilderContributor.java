package org.js.azdanov.springfresh.repositories.operators;

import org.hibernate.boot.MetadataBuilder;
import org.hibernate.boot.spi.MetadataBuilderContributor;
import org.hibernate.dialect.function.SQLFunctionTemplate;
import org.hibernate.type.BooleanType;

public class SqlFunctionsMetadataBuilderContributor implements MetadataBuilderContributor {
  @Override
  public void contribute(MetadataBuilder metadataBuilder) {
    metadataBuilder.applySqlFunction(
        "search_function",
        new SQLFunctionTemplate(BooleanType.INSTANCE, "to_tsvector(?1) @@ plainto_tsquery(?2)"));
  }
}
