package com.yahoo.ailiashenko.mod2;

public class RailwayStation {
	private String name;
	private RegionLabel regionLabel;

	public RailwayStation(String name, RegionLabel regionLabel) {
		super();
		this.name = name;
		this.regionLabel = regionLabel;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public RegionLabel getRegionLabel() {
		return regionLabel;
	}

	public void setRegionLabel(RegionLabel regionLabel) {
		this.regionLabel = regionLabel;
	}

	@Override
	public String toString() {
		return "RailwayStation - " + name.toUpperCase() + "; region - " + regionLabel.name().toUpperCase();
	}

}
