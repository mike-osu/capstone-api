package edu.oregonstate.capstone.configuration;

import org.hibernate.boot.MetadataBuilder;
import org.hibernate.boot.spi.MetadataBuilderContributor;
import org.hibernate.dialect.function.SQLFunctionTemplate;
import org.hibernate.type.BooleanType;

public class SqlFunctionsMetadataBuilderContributor implements MetadataBuilderContributor {

    @Override
    public void contribute(MetadataBuilder metadataBuilder) {

        String template = "to_tsvector(title) @@ plainto_tsquery(?1)"
                + "or to_tsvector(description) @@ plainto_tsquery(?2)"
                + "or to_tsvector(city) @@ plainto_tsquery(?3)"
                + "or to_tsvector(state) @@ plainto_tsquery(?4)"
                + "or to_tsvector(country) @@ plainto_tsquery(?5)";

        metadataBuilder.applySqlFunction("fts", new SQLFunctionTemplate(BooleanType.INSTANCE, template));
    }
}
