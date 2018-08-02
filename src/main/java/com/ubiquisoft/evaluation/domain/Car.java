package com.ubiquisoft.evaluation.domain;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.AbstractMap.SimpleEntry;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Car {

	private String year;
	private String make;
	private String model;

	private List<Part> parts;

	public Map<PartType, Integer> getMissingPartsMap() {
	    // categorize our parts by their type
		Map<PartType, List<Part>> categorized = parts.stream().collect(Collectors.groupingBy(Part::getType));

		// go through all possible types
		return Stream.of(PartType.values())
                // get their expected amount and subtract the amount of parts we have from that
                .map((type) -> new SimpleEntry<>(
                        type,
                        type.getAmount() - categorized.getOrDefault(type, Collections.emptyList()).size()
                ))
                // only account for real differences
                .filter((entry) -> entry.getValue() > 0)
                // collect it as a map
                .collect(Collectors.toMap(SimpleEntry::getKey, SimpleEntry::getValue));
	}

	public List<Part> getDamagedParts() {
	    return parts.stream()
                .filter((p) -> !p.isInWorkingCondition())
                .collect(Collectors.toList());
    }

	@Override
	public String toString() {
		return "Car{" +
				       "year='" + year + '\'' +
				       ", make='" + make + '\'' +
				       ", model='" + model + '\'' +
				       ", parts=" + parts +
				       '}';
	}

	/* --------------------------------------------------------------------------------------------------------------- */
	/*  Getters and Setters *///region
	/* --------------------------------------------------------------------------------------------------------------- */

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getMake() {
		return make;
	}

	public void setMake(String make) {
		this.make = make;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public List<Part> getParts() {
		return parts;
	}

	public void setParts(List<Part> parts) {
		this.parts = parts;
	}

	/* --------------------------------------------------------------------------------------------------------------- */
	/*  Getters and Setters End *///endregion
	/* --------------------------------------------------------------------------------------------------------------- */

}
