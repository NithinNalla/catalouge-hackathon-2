import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class FarmManagementApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter soil type (Loamy/Sandy/Clay):- ");
        String soilType = scanner.nextLine().trim().toLowerCase();

        System.out.print("Enter season (Winter/Summer/Rainy):- ");
        String season = scanner.nextLine().trim().toLowerCase();

        CropRecommendation cropRecommendation = new CropRecommendation();
        cropRecommendation.recommendCrops(soilType, season);

        System.out.print("Enter the selected crop for fertilizer recommendation:- ");
        String crop = scanner.nextLine().trim().toLowerCase();

        FertilizerRecommendation fertilizerRecommendation = new FertilizerRecommendation();
        fertilizerRecommendation.recommendFertilizer(crop);

        IrrigationScheduler irrigationScheduler = new IrrigationScheduler();
        irrigationScheduler.scheduleIrrigation(crop, soilType);

        System.out.print("Enter the area in acres for yield estimation:- ");
        double acres = scanner.nextDouble();
        scanner.nextLine();  // Consume newline

        YieldEstimation yieldEstimation = new YieldEstimation();
        yieldEstimation.estimateYield(crop, soilType, acres);

        CropRotation cropRotation = new CropRotation();
        cropRotation.suggestRotation(crop);

        System.out.print("Does your crop have any disease? (yes/no):- ");
        String hasDisease = scanner.nextLine().trim().toLowerCase();

        if (hasDisease.equals("yes")) {
            System.out.print("Choose a disease from the list (Leaf Blight/Rust/Root Rot):- ");
            String diseaseName = scanner.nextLine().trim().toLowerCase();

            DiseaseIdentification diseaseIdentification = new DiseaseIdentification();
            diseaseIdentification.identifyDisease(diseaseName);
        }

        System.out.print("Do you have any pest problems? (yes/no): -");
        String hasPest = scanner.nextLine().trim().toLowerCase();

        if (hasPest.equals("yes")) {
            System.out.print("Enter the pest affecting your crop (Aphids/Bollworm/Cutworms):- ");
            String pestName = scanner.nextLine().trim().toLowerCase();

            PestManagement pestManagement = new PestManagement();
            pestManagement.recommendPestControl(pestName);
        }

        scanner.close();
    }
}

class CropRecommendation {
    private final Map<String, String[]> seasonCropMap;
    private final Map<String, String[]> soilCropMap;

    public CropRecommendation() {
        seasonCropMap = new HashMap<>();
        soilCropMap = new HashMap<>();

        seasonCropMap.put("winter", new String[]{"wheat", "barley", "peas"});
        seasonCropMap.put("summer", new String[]{"maize", "sorghum", "rice"});
        seasonCropMap.put("rainy", new String[]{"paddy", "millet", "soybean"});

        soilCropMap.put("loamy", new String[]{"wheat", "sugarcane", "cotton"});
        soilCropMap.put("sandy", new String[]{"millet", "peanuts", "potatoes"});
        soilCropMap.put("clay", new String[]{"rice", "soybean", "paddy"});
    }

    public void recommendCrops(String soilType, String season) {
        String[] seasonCrops = seasonCropMap.get(season);
        String[] soilCrops = soilCropMap.get(soilType);

        if (seasonCrops == null || soilCrops == null) {
            System.out.print("Invalid soil type or season entered.");
            return;
        }

        boolean found = false;
        System.out.println("Recommended crops for " + season + " season and " + soilType + " soil are:");
        for (String seasonCrop : seasonCrops) {
            for (String soilCrop : soilCrops) {
                if (seasonCrop.equals(soilCrop)) {
                    System.out.println("- " + seasonCrop);
                    found = true;
                }
            }
        }

        if (!found) {
            System.out.println("No matching crops found for the given soil type and season.");
        }
    }
}

class FertilizerRecommendation {
    private final Map<String, String> cropFertilizerMap;

    public FertilizerRecommendation() {
        cropFertilizerMap = new HashMap<>();
        cropFertilizerMap.put("wheat", "NPK 4:2:1");
        cropFertilizerMap.put("rice", "Urea and DAP");
        cropFertilizerMap.put("soybean", "NPK 3:2:1");
        cropFertilizerMap.put("maize", "NPK 5:2:1");
        cropFertilizerMap.put("barley", "Potash and Phosphate");
    }

    public void recommendFertilizer(String crop) {
        String fertilizer = cropFertilizerMap.get(crop.toLowerCase());
        if (fertilizer != null) {
            System.out.println("Recommended Fertilizer for " + crop + ": " + fertilizer);
        } else {
            System.out.println("No specific fertilizer recommendation available for " + crop + ".");
        }
    }
}

class IrrigationScheduler {
    public void scheduleIrrigation(String crop, String soilType) {
        if (crop.equals("rice") && soilType.equals("clay")) {
            System.out.println("Irrigation recommended every 3 days.");
        } else if (crop.equals("wheat") && soilType.equals("loamy")) {
            System.out.println("Irrigation recommended every 7 days.");
        } else {
            System.out.println("General irrigation advice:- Water the crops when the top 2 inches of soil are dry.");
        }
    }
}

class YieldEstimation {
    public void estimateYield(String crop, String soilType, double acres) {
        double yieldPerAcre;

        if (crop.equals("wheat") && soilType.equals("loamy")) {
            yieldPerAcre = 3.5;  // tons per acre
        } else if (crop.equals("rice") && soilType.equals("clay")) {
            yieldPerAcre = 4.5;  // tons per acre
        } else {
            yieldPerAcre = 2.5;  // default yield per acre
        }

        double totalYield = yieldPerAcre * acres;
        System.out.println("Estimated yield for " + acres + " acres of " + crop + ":- " + totalYield + " tons.");
    }
}

class CropRotation {
    public void suggestRotation(String currentCrop) {
        if (currentCrop.equals("wheat")) {
            System.out.println("Next crop suggestion:- Legumes (e.g., peas, lentils) to fix nitrogen in the soil.");
        } else if (currentCrop.equals("rice")) {
            System.out.println("Next crop suggestion:- Pulses or oilseeds to diversify and improve soil structure.");
        } else {
            System.out.println("Consider rotating with a different crop to prevent soil depletion.");
        }
    }
}

class DiseaseIdentification {
    private final Map<String, String[]> diseaseMap;

    public DiseaseIdentification() {
        diseaseMap = new HashMap<>();
        diseaseMap.put("leaf blight", new String[]{"Fungal", "Use appropriate fungicides."});
        diseaseMap.put("rust", new String[]{"Fungal", "Apply sulfur or copper-based fungicides."});
        diseaseMap.put("root rot", new String[]{"Fungal", "Improve soil drainage and use fungicides."});
    }

    public void identifyDisease(String diseaseName) {
        String[] details = diseaseMap.get(diseaseName);

        if (details != null) {
            System.out.println("Disease Type:- " + details[0]);
            System.out.println("Suggested Treatment:- " + details[1]);
        } else {
            System.out.println("Disease not found.");
        }
    }
}

class PestManagement {
    private final Map<String, String> pestControlMap;

    public PestManagement() {
        pestControlMap = new HashMap<>();
        pestControlMap.put("aphids", "Use neem oil spray or insecticidal soap.");
        pestControlMap.put("bollworm", "Apply Bacillus thuringiensis (Bt) or Spinosad.");
        pestControlMap.put("cutworms", "Use diatomaceous earth or beneficial nematodes.");
    }

    public void recommendPestControl(String pest) {
        String controlMeasure = pestControlMap.get(pest.toLowerCase());
        if (controlMeasure != null) {
            System.out.println("Pest Control for " + pest + ":- " + controlMeasure);
        } else {
            System.out.println("No specific pest control measure available for " + pest + ".");
        }
    }
}
