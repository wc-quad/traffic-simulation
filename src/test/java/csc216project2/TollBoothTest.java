package csc216project2;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * 
 * @author Gabe
 * @version Jul 18, 2010 10:12:31 PM
 */
public class TollBoothTest {
	private Log log = new Log();
	private TollBooth[] tb = new TollBooth[3];
	private Car car1 = new Car(1, 5);
	private Car car2 = new Car(4, 4);
	private Car car3 = new Car(9, 9);
	private EZPass ez1 = new EZPass(11, 2);
	private EZPass ez2 = new EZPass(13, 3);
	private Truck truck1 = new Truck(15, 12);
	private Truck truck2 = new Truck(22, 15);
	
	/**
	 * 
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		// Initialize each tollbooth
		for(int i = 0; i < tb.length; i++) {
			tb[i] = new TollBooth(log);
		}
	}

	/**
	 * Test method for {@link csc216project2.TollBooth#size()}.
	 */
	@Test
	public final void testSize0() {
		
		assertEquals( "tb[1] is not empty",tb[1].size(), 0 );
	}

	/**
	 * Test method for {@link csc216project2.TollBooth#size()}.
	 */
	@Test
	public final void testSize1() {
		tb[1].addVehicle(car3);
		System.out.println(tb[1].departTimeNextVehicle());
		tb[1].addVehicle(ez2);
		System.out.println(tb[1].departTimeNextVehicle());
		assertEquals( "",tb[1].size(), 2 );
	}

	/**
	 * Test method for {@link csc216project2.TollBooth#processNextVehicle()}.
	 */
	@Test
	public final void testProcessNextVehicle() {
		tb[1].addVehicle(car3);
		tb[1].addVehicle(ez2);
		tb[1].processNextVehicle();
		tb[1].processNextVehicle();
		System.out.println( "Depart time next vehicle tb[1]: " + tb[1].departTimeNextVehicle());
		
		assertEquals("", tb[1].departTimeNextVehicle(), Integer.MAX_VALUE);
	}

	/**
	 * Test method for {@link csc216project2.TollBooth#nextVehicle()}.
	 */
	@Test
	public final void testNextVehicle() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link csc216project2.TollBooth#departTimeNextVehicle()}.
	 */
	@Test
	public final void testDepartTimeNextVehicle() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link csc216project2.TollBooth#addVehicle(csc216project2.Vehicle)}.
	 */
	@Test
	public final void testAddVehicle() {
		fail("Not yet implemented");
	}

}
