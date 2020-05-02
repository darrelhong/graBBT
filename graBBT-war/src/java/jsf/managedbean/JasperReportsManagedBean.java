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
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.sql.DataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperRunManager;

/**
 *
 * @author Bryan
 */
@Named(value = "jasperReportsManagedBean")
@ViewScoped
public class JasperReportsManagedBean implements Serializable
{

    @Resource(name = "graBBTDataSource")
    private DataSource graBBTDataSource;

    /**
     * Creates a new instance of JasperReportsManagedBean
     */
    public JasperReportsManagedBean() 
    {
    }
    
    public void generateReport(ActionEvent event)
    {
        try {
            InputStream reportStream = FacesContext.getCurrentInstance().getExternalContext().getResourceAsStream("/jasperreports/Retailer_Sales_Reports.jasper");
            OutputStream outputStream = FacesContext.getCurrentInstance().getExternalContext().getResponseOutputStream();
        
            JasperRunManager.runReportToPdfStream(reportStream, outputStream, new HashMap<>(), graBBTDataSource.getConnection());
        } catch (IOException | JRException | SQLException ex) {
            Logger.getLogger(JasperReportsManagedBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
}
