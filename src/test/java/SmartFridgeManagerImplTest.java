import com.app.mypack.controller.impl.SmartFridgeManagerImpl;
import com.app.mypack.entity.SmartFridgeItem;
import com.app.mypack.service.id.impl.UUIDManagerImpl;
import com.app.mypack.service.validator.SmartFridgeBeanValidator;
import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SmartFridgeManagerImplTest {

    @InjectMocks
    private SmartFridgeManagerImpl smartFridgeManager = new SmartFridgeManagerImpl();

    @Mock
    private SmartFridgeBeanValidator<SmartFridgeItem> smartFridgeBeanValidator;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    //handleItemAdded
    //@Test()
    public void testA () {

        final long iItemType  = 1L;
        final String iItemUUID = new UUIDManagerImpl().generateItemUUID();
        final String iName = "ItemName";
        Double iFillFactor = 1.0;

        smartFridgeManager = new SmartFridgeManagerImpl();
        Mockito.doNothing().when(smartFridgeBeanValidator).validate(Mockito.any(SmartFridgeItem.class));
        smartFridgeManager.handleItemAdded(iItemType, iItemUUID, iName,iFillFactor);

    }

    //getItems()
    //@Test()
    public void testB () {
        Object[] objects = smartFridgeManager.getItems(0.5);
        Assert.assertNotNull(objects);
    }

    //getFillFactor()
    //@Test()
    public void testC () {
        Double fillFactor = smartFridgeManager.getFillFactor(1L);
        Assert.assertNotNull(fillFactor);
    }


}
