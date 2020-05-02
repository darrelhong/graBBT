/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedbean;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.logging.Level;
import javax.annotation.Resource;
import javax.faces.bean.RequestScoped;
import javax.inject.Named;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.sql.DataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperRunManager;

/**
 *
 * @author Bryan
 */
@Named(value = "jasperReportManagedBean")
@RequestScoped
public class JasperReportManagedBean implements Serializable 
{

    @Resource(name = "graBBTDataSource")
    private DataSource graBBTDataSource;

    /**
     * Creates a new instance of JasperReportManagedBean
     */
    public JasperReportManagedBean() {
    }
    
    public void generateReport(ActionEvent event)
    {
        System.out.println("*****GENERATING REPORT*******");
        try {
            InputStream reportStream = FacesContext.getCurrentInstance().getExternalContext().getResourceAsStream("/jasperreports/Retailer_Sales_Reports.jasper");
            OutputStream outputStream = FacesContext.getCurrentInstance().getExternalContext().getResponseOutputStream();
            
            JasperRunManager.runReportToPdfStream(reportStream, outputStream, new HashMap<>(), graBBTDataSource.getConnection());
        } catch (IOException | JRException | SQLException ex) {
            java.util.logging.Logger.getLogger(JasperReportManagedBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
}
