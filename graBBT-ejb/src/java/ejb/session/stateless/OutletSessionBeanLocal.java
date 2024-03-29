/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.OutletEntity;
import java.util.List;
import javax.ejb.Local;
import util.exception.DeactivateOutletException;
import util.exception.InputDataValidationException;
import util.exception.OutletNameExistsException;
import util.exception.OutletNotFoundException;
import util.exception.RetailerNotFoundException;
import util.exception.UnknownPersistenceException;
import util.exception.UpdateOutletException;

/**
 *
 * @author Chloe Tanny
 */
@Local
public interface OutletSessionBeanLocal {
    
    public List<OutletEntity> retrieveAllOutletsByRetailerId(Long retailerId);
    
    public Long createNewOutlet(OutletEntity newOutletEntity, Long retailerId) throws OutletNameExistsException, RetailerNotFoundException, UnknownPersistenceException, InputDataValidationException;

    public OutletEntity retrieveOutletByOutletId(Long outletId) throws OutletNotFoundException;

    public void deactivateOutlet(Long outletId) throws OutletNotFoundException, DeactivateOutletException;

    public void updateOutlet(OutletEntity outletEntity, List<Long> listingIds) throws OutletNotFoundException, InputDataValidationException, UpdateOutletException;
    
    public List<OutletEntity> retrieveAllOutlets();
}
