package panda.runtime.battery;

import panda.runtime.util.OsUtil;

public class BatterySupply {
	private BareMetalBattery bareMetalBattery;
	
	public BatterySupply() {
		// Load specific OS version for BareMetalBattery
		switch(OsUtil.getOsType()) {
			case WINDOWS:
				System.err.println("Windows not supported. Exiting.");
				System.exit(1);
			case MACOS:
				bareMetalBattery = new OSXBareMetalBattery();
        break;
			case LINUX:
				bareMetalBattery = new UnixBareMetalBattery();
				break;
			case ANDROID:
				System.err.println("Windows not supported. Exiting.");
				System.exit(1);
				break;
			case NONE:
				System.err.println("Encountered unsupported operating system. Exiting.");
				System.exit(1);
		}

	}
	
	public int getRemainingCapacity() {
		return bareMetalBattery.getRemainingCapacity();
	}

	public int getTotalCapacity() {
		return bareMetalBattery.getTotalCapacity();
	}

  public float percentRemaining() {
    return ((float) this.getRemainingCapacity()) / ((float) this.getTotalCapacity());
  }
	
}
