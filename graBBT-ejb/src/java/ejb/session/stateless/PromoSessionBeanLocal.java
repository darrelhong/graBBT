/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.PromoEntity;
import java.util.List;
import javax.ejb.Local;
import util.exception.InputDataValidationException;
import util.exception.PromoClaimedByCustomerAlreadyException;
import util.exception.PromoNoLongerActiveException;
import util.exception.PromoNotClaimedByCustomer;
import util.exception.PromoNotFoundException;
import util.exception.PromoUsedAlreadyException;
import util.exception.UnknownPersistenceException;

/**
 *
 * @author Chloe Tanny
 */
@Local
public interface PromoSessionBeanLocal {
    public PromoEntity createNewPromo(PromoEntity newPromoEntity) throws UnknownPersistenceException, InputDataValidationException;
    public List<PromoEntity> retrieveAllActivePromos(); 
    public List<PromoEntity> retrieveAllPromos();
    public void deactivatePromo(Long promoId) throws PromoNotFoundException;
    public PromoEntity retrievePromoById(Long promoId) throws PromoNotFoundException;
    public void updatePromo(PromoEntity promo) throws InputDataValidationException, PromoNotFoundException;
    public void customerClaimsPromo(Long customerId, Long promoId) throws PromoNotFoundException, PromoNoLongerActiveException, PromoClaimedByCustomerAlreadyException;
    public List<PromoEntity> retrievePromosInCustomerWallet(Long customerId);
    public PromoEntity customerUsesPromo(Long customerId, Long promoId) throws PromoNotFoundException, PromoNotClaimedByCustomer, PromoUsedAlreadyException;
    
}
