#!/bin/bash

# Nombre del proyecto y paquete
PROYECTO="pdf-table-extractor"
PAQUETE="com.ejemplo"
CLASE="PdfTableExtractor"

# Convertir paquete en ruta
PAQUETE_RUTA=$(echo "$PAQUETE" | tr '.' '/')

# Crear carpetas
mkdir -p "$PROYECTO/src/main/java/$PAQUETE_RUTA"
mkdir -p "$PROYECTO/src/main/resources"

# Crear pom.xml
cat > "$PROYECTO/pom.xml" <<EOF
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 
                             http://maven.apache.org/xsd/maven-4.0.0.xsd">

    <modelVersion>4.0.0</modelVersion>
    <groupId>com.ejemplo</groupId>
    <artifactId>pdf-table-extractor</artifactId>
    <version>1.0-SNAPSHOT</version>

    <dependencies>
        <dependency>
            <groupId>technology.tabula</groupId>
            <artifactId>tabula</artifactId>
            <version>1.0.4</version>
        </dependency>

        <dependency>
            <groupId>org.apache.pdfbox</groupId>
            <artifactId>pdfbox</artifactId>
            <version>2.0.27</version>
        </dependency>

        <dependency>
            <groupId>org.slf4j</groupId>
            <artifactId>slf4j-simple</artifactId>
            <version>2.0.9</version>
        </dependency>

        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <version>1.18.30</version>
            <scope>provided</scope>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-compiler-plugin</artifactId>
                <version>3.11.0</version>
                <configuration>
                    <source>17</source>
                    <target>17</target>
                </configuration>
            </plugin>
        </plugins>
    </build>
</project>
EOF

# Crear clase Java
cat > "$PROYECTO/src/main/java/$PAQUETE_RUTA/$CLASE.java" <<EOF
package $PAQUETE;

import technology.tabula.Page;
import technology.tabula.Table;
import technology.tabula.extractors.BasicExtractionAlgorithm;
import technology.tabula.extractors.ExtractionAlgorithm;
import technology.tabula.ObjectExtractor;
import technology.tabula.PageIterator;
import org.apache.pdfbox.pdmodel.PDDocument;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class $CLASE {

    public static void main(String[] args) throws IOException {
        File pdfFile = new File("ejemplo.pdf");
        try (PDDocument document = PDDocument.load(pdfFile)) {
            ObjectExtractor extractor = new ObjectExtractor(document);
            PageIterator iterator = extractor.extract();
            ExtractionAlgorithm algorithm = new BasicExtractionAlgorithm();

            while (iterator.hasNext()) {
                Page page = iterator.next();
                List<Table> tables = algorithm.extract(page);

                for (Table table : tables) {
                    System.out.println(table.getRows().size() + " filas encontradas:");
                    System.out.println(table.toString());
                }
            }
        }
    }
}
EOF

echo "✅ Proyecto '$PROYECTO' creado con éxito."

