package com.ubiquisoft.evaluation;

import com.ubiquisoft.evaluation.domain.Car;
import com.ubiquisoft.evaluation.domain.ConditionType;
import com.ubiquisoft.evaluation.domain.Part;
import com.ubiquisoft.evaluation.domain.PartType;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

public class CarDiagnosticEngine {

	public void executeDiagnostics(Car car) {
		// small implementation detail: bitwise operator used
        // so that each one gets printed out if applicable,
        // otherwise it would prematurely exit and not evaluate
        // the other fields and print their results
		if (ensureNotNull(car.getMake(), "Make is not present") |
                ensureNotNull(car.getModel(), "Model is not present") |
                ensureNotNull(car.getYear(), "Year is not present")) {
		    return;
        }

        Map<PartType, Integer> missingParts = car.getMissingPartsMap();

		if (!missingParts.isEmpty()) {
		    missingParts.forEach(this::printMissingPart);
		    return;
        }

        List<Part> damagedParts = car.getDamagedParts();

		if (!damagedParts.isEmpty()) {
		    damagedParts.forEach((part) -> printDamagedPart(part.getType(), part.getCondition()));
		    return;
        }

        System.out.println("Successfully finished diagnostics. Car is in good shape!");
	}

	private boolean ensureNotNull(Object obj, String message) {
	    if (obj == null) {
	        System.out.println(message);
	        return true;
        }

        return false;
    }

	private void printMissingPart(PartType partType, Integer count) {
		if (partType == null) throw new IllegalArgumentException("PartType must not be null");
		if (count == null || count <= 0) throw new IllegalArgumentException("Count must be greater than 0");

		System.out.println(String.format("Missing Part(s) Detected: %s - Count: %s", partType, count));
	}

	private void printDamagedPart(PartType partType, ConditionType condition) {
		if (partType == null) throw new IllegalArgumentException("PartType must not be null");
		if (condition == null) throw new IllegalArgumentException("ConditionType must not be null");

		System.out.println(String.format("Damaged Part Detected: %s - Condition: %s", partType, condition));
	}

	public static void main(String[] args) throws JAXBException {
		// Load classpath resource
		InputStream xml = ClassLoader.getSystemResourceAsStream("SampleCar.xml");

		// Verify resource was loaded properly
		if (xml == null) {
			System.err.println("An error occurred attempting to load SampleCar.xml");

			System.exit(1);
		}

		// Build JAXBContext for converting XML into an Object
		JAXBContext context = JAXBContext.newInstance(Car.class, Part.class);
		Unmarshaller unmarshaller = context.createUnmarshaller();

		Car car = (Car) unmarshaller.unmarshal(xml);

		// Build new Diagnostics Engine and execute on deserialized car object.

		CarDiagnosticEngine diagnosticEngine = new CarDiagnosticEngine();

		diagnosticEngine.executeDiagnostics(car);

	}

}
