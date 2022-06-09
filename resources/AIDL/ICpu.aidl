package vendor.eightman.cpu;

@VintfStability
interface ICpu {
    // Adding return type to method instead of out param String scalingGovernor since there is only one return value.
    String getScalingGovernor();

    // Adding return type to method instead of out param boolean success since there is only one return value.
    boolean setScalingGovernor(in String governor);
}
