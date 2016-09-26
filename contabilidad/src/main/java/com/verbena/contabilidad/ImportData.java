package com.verbena.contabilidad;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.zkoss.zk.ui.select.annotation.VariableResolver;

import com.verbena.contabilidad.entity.Concepto;
import com.verbena.contabilidad.entity.Importe;
import com.verbena.contabilidad.services.ConceptoService;
import com.verbena.contabilidad.services.ImporteService;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class ImportData {
	public void main(ConceptoService conceptoService, ImporteService importeService) throws IOException, ParseException {
		try {
			importConceptos(conceptoService);
		} catch (Exception e) {
			System.out.println("Error: " +e.getMessage());
		}
		try {
			importImportesEntrada(conceptoService, importeService);
		} catch (Exception e) {
			System.out.println("Error: " +e.getMessage());
		}
		try {
			importImportesSalida(conceptoService, importeService);
		} catch (Exception e) {
			System.out.println("Error: " +e.getMessage());
		}
	}

	/**
	 * @param conceptoService
	 * @throws IOException
	 */
	private void importConceptos(ConceptoService conceptoService) throws IOException {
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader("/Users/vicente/workspace/contabilidad/src/main/java/com/verbena/contabilidad/file.txt"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
  
		String line = br.readLine();

	    while (line != null) {
	        line = br.readLine();
	        String[] split = line.split(",");
	        Concepto concepto = new Concepto();
	        concepto.setDescripcion(split[0]);
			concepto.setGastoFijo(Boolean.valueOf(split[1]));
			concepto = conceptoService.addConcepto(concepto);
	    }
	}
	
	/**
	 * @param conceptoService
	 * @throws IOException
	 * @throws ParseException 
	 */
	private void importImportesEntrada(ConceptoService conceptoService, ImporteService importeService) throws IOException, ParseException {
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader("/Users/vicente/workspace/contabilidad/src/main/java/com/verbena/contabilidad/file1.txt"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
  
		String line = br.readLine();
		Concepto concepto = new Concepto();
        concepto.setDescripcion("ENTRADA");
		concepto.setGastoFijo(false);
		concepto = conceptoService.addConcepto(concepto);
		
	    while (line != null) {
	        line = br.readLine();
	        String[] split = line.split("#");
	        Importe importe = new Importe();
	        importe.setConcepto_id(concepto);
	        importe.setEfectivo(true);
	        importe.setEntrada(true);
	        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");
	        importe.setFecha(sdf.parse(split[0]));
	        importe.setCantidad(Double.valueOf(split[1]));
	        importe.setExtra(Boolean.valueOf(split[2]));
	        
	        importeService.addImporte(importe);
	    }
	}
	/**
	 * @param conceptoService
	 * @throws IOException
	 * @throws ParseException 
	 */
	private void importImportesSalida(ConceptoService conceptoService, ImporteService importeService) throws IOException, ParseException {
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader("/Users/vicente/workspace/contabilidad/src/main/java/com/verbena/contabilidad/file3.txt"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
  
		String line = br.readLine();
        	
	    while (line != null) {
	        line = br.readLine();
	        String[] split = line.split("#");
	        Importe importe = new Importe();
	        Concepto concepto = conceptoService.findByDescripcion(split[0]);
	        if(concepto == null){
	        	concepto = new Concepto();
	        	concepto.setDescripcion(split[0]);
	    		concepto.setGastoFijo(false);
	    		concepto = conceptoService.addConcepto(concepto);
	        }
	        importe.setConcepto_id(concepto);
	        importe.setEfectivo(true);
	        importe.setEntrada(false);
	        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");
	        importe.setFecha(sdf.parse(split[1]));
	        importe.setCantidad(Double.valueOf(split[2]));
	        importe.setExtra(Boolean.valueOf(split[4]));
	        
	        importeService.addImporte(importe);
	    }
	}
}
