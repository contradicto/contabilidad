/**
 * 
 */
package com.verbena.contabilidad;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Button;
import org.zkoss.zul.Calendar;
import org.zkoss.zul.Chart;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.ComboitemRenderer;
import org.zkoss.zul.Doublebox;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Radio;

import com.verbena.contabilidad.entity.Concepto;
import com.verbena.contabilidad.entity.Importe;
import com.verbena.contabilidad.entity.Plantilla;
import com.verbena.contabilidad.entity.Turno;
import com.verbena.contabilidad.services.ConceptoService;
import com.verbena.contabilidad.services.ImporteService;
import com.verbena.contabilidad.services.PlantillaService;
import com.verbena.contabilidad.services.TurnoService;

/**
 * @author Vicente
 *
 */
@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class IndexController extends SelectorComposer<Component>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Wire
    private Calendar calendar;
	@Wire
    private Combobox cmbxConcepto;
	@Wire
    private Combobox cmbxPlantilla;
	@Wire
    private Doublebox dblbxImporte;
	@Wire
	private Radio rdEntrada;
	@Wire
	private Radio rdSalida;
	@Wire
	private Radio rdEfectivo;
	@Wire
	private Radio rdTarjeta;
	@Wire
	private Radio rdA;
	@Wire
	private Radio rdB;
	@Wire
	private Radio rdManiana;
	@Wire
	private Radio rdTarde;
	@Wire
	private Radio rdNoche;
	@Wire
	private Listbox entradasListbox;
	@Wire
	private Listbox salidasListbox;
	@Wire
	private Listbox plantillaListbox;
	@Wire
	private Radio rdGastoFijo;
	@Wire
	private Button add;
	@Wire
    Chart ringchart;
	@Wire
    Label totalIngresos;
	@Wire
    Label totalGastos;
	@Wire
    Label totalGastosFijo;
	@Wire
    Label saldoFinal;
	@Wire
	Label saldoFinalB;
	@WireVariable
	private ConceptoService conceptoService;
	@WireVariable
	private ImporteService importeService;
	@WireVariable
	private PlantillaService plantillaService;
	@WireVariable
	private TurnoService turnoService;
	
	//Formateador de doubles
	DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.FRANCE);
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		//Establecemos los renders de combos y listados
		ImportData importData = new ImportData();
		importData.main(conceptoService, importeService);
		setComboRenderer();
		setListboxRenderers();
		//Cargamos todos los conceptos existentes
		cmbxConcepto.setModel(new ListModelList<Concepto>(conceptoService.getConceptos()));
		//Cargamos todos los miembros de la plantilla
		cmbxPlantilla.setModel(new ListModelList<Plantilla>(plantillaService.getPlantillas()));
		//Cargamos listados en base a la fecha del calendario
		changeCalendar();
		//Calculamos los datos de la cabecera
		calculateHeader();
	}
	/**
	 * Calcula los valores de la cabecera de la pagina
	 */
	private void calculateHeader() {
		//Total Ingresos
		Double totalI = importeService.getTotalMes(calendar.getValue(), Boolean.TRUE, Boolean.FALSE);
		totalIngresos.setValue(addEuros(formatter.format(round(totalI))));
		//Total Gastos
		Double totalG = importeService.getTotalMes(calendar.getValue(), Boolean.FALSE, Boolean.FALSE);
		totalGastos.setValue(addEuros(formatter.format(round(totalG))));
		//Total Gastos Fijos
		Double totalF = importeService.getTotalMesFijo(calendar.getValue());
		totalGastosFijo.setValue(addEuros(formatter.format(round(totalF))));
		//Saldo final
        Double saldoF = totalI - totalG - totalF;
        saldoFinal.setValue(addEuros(formatter.format(round(saldoF))));
        //Saldo final extra
        Double entradaB = importeService.getTotalMes(calendar.getValue(), Boolean.TRUE, Boolean.TRUE);
        Double salidaB = importeService.getTotalMes(calendar.getValue(), Boolean.FALSE, Boolean.TRUE);
        Double saldoB = entradaB-salidaB;
        saldoFinalB.setValue(addEuros(formatter.format(round(saldoB))));
	}
	
	private String addEuros(String valor){
		return valor + " €";
	}
	/**
	 * Redondea al alza con dos decimales
	 * @param value
	 * @return
	 */
	private static Double round(Double value) {
	    BigDecimal bd = new BigDecimal(value);
	    bd = bd.setScale(2, RoundingMode.HALF_UP);
	    return bd.doubleValue();
	}
	/**
	 * Establecemos los renderers de los listados de entradas y salidas
	 */
	private void setListboxRenderers() {
		entradasListbox.setItemRenderer(new ListitemRenderer<Importe>() {
			public void render(Listitem item, Importe importe, int index) throws Exception {
				newListcell(importe.getConcepto_id().getDescripcion(), item);
				newListcell(importe.getCantidad().toString(), item);
				newListcell(importe.getEfectivo() ? "Efectivo":"Tarjeta", item);
				newListcell(importe.getExtra() ? "Si":"No", item);
				newListcell(importe.getConcepto_id().getGastoFijo() ? "Si":"No", item);
				item.setValue(importe);
				doubleClickItem(item);
			}
		});
		
		salidasListbox.setItemRenderer(new ListitemRenderer<Importe>() {
			public void render(Listitem item, Importe importe, int index) throws Exception {
				newListcell(importe.getConcepto_id().getDescripcion(), item);
				newListcell(importe.getCantidad().toString(), item);
				newListcell(importe.getEfectivo() ? "Efectivo":"Tarjeta", item);
				newListcell(importe.getExtra() ? "Si":"No", item);
				newListcell(importe.getConcepto_id().getGastoFijo() ? "Si":"No", item);
				item.setValue(importe);
				doubleClickItem(item);
			}
		});
		
		plantillaListbox.setItemRenderer(new ListitemRenderer<Turno>() {
			public void render(Listitem item, Turno turno, int index) throws Exception {
				newListcell(turno.getPlantilla().getNombre(), item);
				newListcell(turno.getTurno(), item);
				item.setValue(turno);
				doubleClickItem(item);
			}
		});
	}
	
	/**
	 * Establecemos el evento doble click para cada item del listado de manera que cargue la info en la parte izquierda
	 * @param item
	 */
	private void doubleClickItem(Listitem item) {
		item.addEventListener(Events.ON_DOUBLE_CLICK, new EventListener<Event>() {

			public void onEvent(Event event) throws Exception {
				Listitem li = (Listitem) event.getTarget();
				Importe importe = (Importe)li.getValue();
				cmbxConcepto.setValue(importe.getConcepto_id().getDescripcion());
				dblbxImporte.setValue(importe.getCantidad());
				if(importe.getEntrada()){
					rdEntrada.setChecked(Boolean.TRUE);
				}else{
					rdSalida.setChecked(Boolean.TRUE);
				}
				if(importe.getEfectivo()){
					rdEfectivo.setChecked(Boolean.TRUE);
				}else{
					rdSalida.setChecked(Boolean.TRUE);
				}
				if(importe.getExtra()){
					rdB.setChecked(Boolean.TRUE);
				}else{
					rdA.setChecked(Boolean.TRUE);
				}
				if(importe.getConcepto_id().getGastoFijo()){
					rdGastoFijo.setChecked(Boolean.TRUE);
				}else{
					rdGastoFijo.setChecked(Boolean.FALSE);
				}
				
				importeService.deleteImporte(importe);
				changeCalendar();
				calculateHeader();
			}
		});
	}
	
	/**
	 * Crea una nueva celda con el label pasado por parametro
	 * @param value
	 */
	private void newListcell(String value, Listitem item) {
		Listcell cell = new Listcell();
		Label label = new Label(value);
		cell.appendChild(label);
		item.appendChild(cell);
	}
	
	/**
	 * Establece el renderer del combo de conceptos 
	 */
	private void setComboRenderer() {
		cmbxConcepto.setItemRenderer(new ComboitemRenderer<Concepto>() {
			public void render(Comboitem item, Concepto data, int arg2) throws Exception {
				item.setLabel(data.getDescripcion());	
				item.setValue(data);
			}
		});
		
		cmbxPlantilla.setItemRenderer(new ComboitemRenderer<Plantilla>() {
			public void render(Comboitem item, Plantilla data, int arg2) throws Exception {
				item.setLabel(data.getNombre());	
				item.setValue(data);
			}
		});
	}
	
	/**
	 * Evento onclick del boton añadir importe
	 */
	@Listen("onClick = #add")
    public void add(){
		Importe importe = new Importe();
		importe.setCantidad(dblbxImporte.getValue());
		importe.setFecha(calendar.getValue());
		importe.setExtra(rdB.isChecked());
		importe.setEntrada(rdEntrada.isChecked());
		importe.setEfectivo(rdEfectivo.isChecked());
		
		//Buscamos el concepto en BBDD por su descripcion
        Concepto concepto = conceptoService.findByDescripcion(cmbxConcepto.getValue());
        //Si no existe se crea
        if(concepto==null){
        	concepto = addConcepto();
        }
        
        importe.setConcepto_id(concepto);
        importe.setEntrada(rdEntrada.isChecked());
        
        if(rdEfectivo.isChecked()){
        	importe.setEfectivo(Boolean.TRUE);
        }else{
        	importe.setEfectivo(Boolean.FALSE);
        }
        
        importe = importeService.addImporte(importe);
        
        changeCalendar();
        calculateHeader();
        clean();
    }
	
	/**
	 * Evento onclick del boton añadir plantilla
	 */
	@Listen("onClick = #addPlantilla")
    public void addPlantilla(){
		
		//Buscamos el concepto en BBDD por su descripcion
        Plantilla plantilla = plantillaService.findByNombre(cmbxPlantilla.getValue());
        //Si no existe plantilla se crea
        if(plantilla==null){
        	plantilla = new Plantilla();
        	plantilla.setNombre(cmbxPlantilla.getValue());
        	plantilla = plantillaService.addPlantilla(plantilla);
        }
        
       Turno turno = new Turno();
       turno.setFecha(calendar.getValue());
       turno.setPlantilla(plantilla);
       if(rdManiana.isChecked()){
           turno.setTurno("Mañana");
       }else if(rdTarde.isChecked()){
           turno.setTurno("Tarde");    	   
       }else{
           turno.setTurno("Noche");
       }

       turnoService.addTurno(turno);
       
       //Cargamos listado de entradas
       plantillaListbox.setModel(new ListModelList<Turno>(turnoService.findByFecha(calendar.getValue())));
       cmbxPlantilla.setValue(null);
    }
	
	/**
	 * Resetea la parte izquierda
	 */
	private void clean() {
		//Cargamos todos los conceptos existentes
      	cmbxConcepto.setModel(new ListModelList<Concepto>(conceptoService.getConceptos()));
        cmbxConcepto.setValue(null);
        dblbxImporte.setValue(null);
        rdSalida.setChecked(Boolean.TRUE);
        rdEfectivo.setChecked(Boolean.TRUE);
        rdA.setChecked(Boolean.TRUE);
	}
	
	/**
	 * Añade el concepto a BBDD
	 * @return
	 */
	private Concepto addConcepto() {
		Concepto concepto;
		concepto = new Concepto();
		concepto.setDescripcion(cmbxConcepto.getValue());
		concepto.setGastoFijo(rdGastoFijo.isChecked());
		concepto = conceptoService.addConcepto(concepto);
		return concepto;
	}
	
	/**
	 * Evento de on change del calendario
	 */
	@Listen("onChange = #calendar")
    public void changeCalendar(){
		//Cargamos listado de entradas
		entradasListbox.setModel(new ListModelList<Importe>(importeService.getImportebyFecha(calendar.getValue(), Boolean.TRUE)));
		//Cargarmos listado de salidas
		salidasListbox.setModel(new ListModelList<Importe>(importeService.getImportebyFecha(calendar.getValue(), Boolean.FALSE)));
		//Cargamos listado de entradas
	    plantillaListbox.setModel(new ListModelList<Turno>(turnoService.findByFecha(calendar.getValue())));
		//recargamos cabcera
		calculateHeader();
    }
}
