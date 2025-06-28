package com.limatisoft.base.repository.file;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.limatisoft.base.model.Product;
import com.limatisoft.base.repository.ProductRepository;
import com.limatisoft.config.FilePaths;

public class ProductFileRepository implements ProductRepository{
	public void save(Product product) {
		File file = new File(FilePaths.BASE_DIRECTORY, FilePaths.PRODUCT_FILE);
		FileOutputStream fileOutputStream = null;
		try {
			fileOutputStream = new FileOutputStream(file, true);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		try(
			PrintWriter printWriter = new PrintWriter(fileOutputStream);
			) {
			Long productIdGenerated = new Date().getTime(); 
			String line = String.format("%d;%s;%s;%f", 
											productIdGenerated,
											product.getCode(),
											product.getName(),
											product.getSalesPrice());
			printWriter.println(line);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void update(Product product) {
		File inputFile = new File(FilePaths.BASE_DIRECTORY,FilePaths.PRODUCT_FILE);
        File tempFile = new File(FilePaths.BASE_DIRECTORY, "productos_temp.txt");
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
             BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {
            String line;
            String codigoABuscar = product.getCode();
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");
                String codeInFile = parts[1];
                if (codeInFile.equalsIgnoreCase(codigoABuscar)) {
                    writer.write(product.getId() + ";" + product.getCode() + ";" + product.getName() + ";" + product.getSalesPrice());
                } else {
                    writer.write(line);
                }
                writer.newLine();
            }
        } catch (IOException e) {
           throw new RuntimeException("se produjo un error al actualizar");
        }
        inputFile.delete();
        tempFile.renameTo(inputFile);
	}
	
	public void delete(Product product) {
		File inputFile = new File(FilePaths.BASE_DIRECTORY,FilePaths.PRODUCT_FILE);
		File tempFile = new File(FilePaths.BASE_DIRECTORY, FilePaths.PRODUCT_TEMP_FILE);
		try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
		     BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {
		    String line;
		    String codigoABuscar = product.getCode();
		    while ((line = reader.readLine()) != null) {
		        String[] parts = line.split(";");
		        String codeInFile = parts[1];
		        if (codeInFile.equalsIgnoreCase(codigoABuscar)) {
		        	continue;
		        }
		        writer.write(line);
		        writer.newLine();
		    }
		} catch (IOException e) {
		   throw new RuntimeException("se produjo un error al actualizar");
		}
		inputFile.delete();
		tempFile.renameTo(inputFile);	
	}
	
	public Product findByCode(String code) {
		File file = new File(FilePaths.BASE_DIRECTORY, FilePaths.PRODUCT_FILE);
		try(
			FileReader fileReader = new FileReader(file);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			) {
			String line = null;
			while( (line = bufferedReader.readLine()) != null) {	//01;Galleta;2.0
				String[] parts = line.split(";");
				String codeInFile = parts[1];
				if(code.equalsIgnoreCase(codeInFile)) {
					Product producto = new Product();
					producto.setId(Long.valueOf(parts[0]));
					producto.setCode(code);
					producto.setName(parts[2]);
					producto.setSalesPrice(new BigDecimal(parts[3]));
					return producto;
				}
			}
		} catch (FileNotFoundException e) {
		} catch (IOException e) {
		}
		return null;
	}
	 
	public List<Product> findAll() {
		File file = new File(FilePaths.BASE_DIRECTORY, FilePaths.PRODUCT_FILE);
		ArrayList<Product> productos = new ArrayList<Product>();
		try (
			FileReader fileReader = new FileReader(file);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			){
			
			String line = null;
			while( (line = bufferedReader.readLine()) != null) {	//01;Galleta;2.0
				String[] parts = line.split(";");
				String codigo = parts[1];
				Product producto = new Product();
				producto.setId(Long.valueOf(parts[0]));
				producto.setCode(codigo);
				producto.setName(parts[2]);
				producto.setSalesPrice(new BigDecimal(parts[3]));
				productos.add(producto);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return productos;
	}
	
	public Product findByCodeAndIdNot(String code, Long id) {
	    File file = new File(FilePaths.BASE_DIRECTORY, FilePaths.PRODUCT_FILE);
	    try (
	        FileReader fileReader = new FileReader(file);
	        BufferedReader bufferedReader = new BufferedReader(fileReader);
	    ) {
	        String line = null;
	        while ((line = bufferedReader.readLine()) != null) {  
	            String[] parts = line.split(";");
	            if (parts.length < 4) continue; // Prevención por si la línea está incompleta

	            Long idInFile = Long.valueOf(parts[0]);
	            String codeInFile = parts[1];

	            if (code.equalsIgnoreCase(codeInFile) && !id.equals(idInFile)) {
	                Product producto = new Product();
	                producto.setId(idInFile);
	                producto.setCode(codeInFile);
	                producto.setName(parts[2]);
	                producto.setSalesPrice(new BigDecimal(parts[3]));
	                return producto;
	            }
	        }
	    } catch (FileNotFoundException e) {
	        e.printStackTrace();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	    return null;
	}
}
