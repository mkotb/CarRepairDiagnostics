package com.ubiquisoft.evaluation.domain;

public enum PartType {
	ENGINE, ELECTRICAL, TIRE(4), FUEL_FILTER, OIL_FILTER;

	private int amount;

	PartType() {
	    this(1);
    }

    PartType(int amount) {
	    this.amount = amount;
    }

    public int getAmount() {
        return amount;
    }
}
