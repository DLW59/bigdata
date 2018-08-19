import com.dlw.bigdata.service.PersonService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author dlw
 * @date 2018/8/19
 * @desc
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring.xml")
public class PersonTest {
    @Autowired
    private PersonService personService;
    @Test
    public void testInfo() {
        String s = personService.getInfo("张三", 20);
        System.out.println(s);
    }
}
