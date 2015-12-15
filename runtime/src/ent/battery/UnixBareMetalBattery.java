package ent.runtime.battery;

import ent.runtime.battery.BareMetalBattery;

import java.io.IOException;
import java.io.FileReader;
import java.io.BufferedReader;

public class UnixBareMetalBattery implements BareMetalBattery {

  private static final String CURRENT_ENERGY_PATH = 
    "/sys/class/power_supply/BAT0/energy_now";

  private static final String TOTAL_ENERGY_PATH = 
    "/sys/class/power_supply/BAT0/energy_full";

  public UnixBareMetalBattery() {
  }
	
  public int getRemainingCapacity() {
    int remainingCapacity = 0;

    try {
      remainingCapacity = this.getBareMetalCurrent();
    } catch (IOException e) {
      // TODO: How to handle?
    }

    return remainingCapacity;
  }  

  public int getTotalCapacity() {
    int totalCapacity = 0;

    try {
      totalCapacity = this.getBareMetalTotal();
    } catch (IOException e) {
      // TODO: How to handle?
    }

    return totalCapacity;
  }  

  private int getBareMetalCurrent() throws IOException {
    FileReader fileReader = new FileReader(CURRENT_ENERGY_PATH);
    BufferedReader bufferedReader = new BufferedReader(fileReader);

    int currentEnergy = Integer.parseInt(bufferedReader.readLine());

    return currentEnergy;
  }

  private int getBareMetalTotal() throws IOException{
    FileReader fileReader = new FileReader(TOTAL_ENERGY_PATH);
    BufferedReader bufferedReader = new BufferedReader(fileReader);

    int currentEnergy = Integer.parseInt(bufferedReader.readLine());

    return currentEnergy;
  }

}
