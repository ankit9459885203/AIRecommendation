package org.example;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.UserBasedRecommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;

import java.io.File;
import java.io.IOException;
import java.util.List;



// note : - dont run it from here ,  it will not work because compiler checks for compiled files only. but loacation of 
//          data.csv is diffrent. Hence go for command   ./gradlew run , you can change the id number in line number 41.

public class RecommendationApp {
    public static void main(String[] args) {
        System.out.println("Recommendation System Started.\n");

        try {
            // Step 1: Load CSV into Mahout's data model
            DataModel model = new FileDataModel(new File("src/main/resources/data.csv"));

            // Step 2: Calculate similarity between users
            UserSimilarity similarity = new PearsonCorrelationSimilarity(model);

            // Step 3: Define neighborhood (N closest users)
            // this 2 will tell - between how many people you are doing the comparison, 
            //if i will make it 5 then comparison will be between 5 people from data.scv file
            UserNeighborhood neighborhood = new NearestNUserNeighborhood(2, similarity, model);

            // Step 4: Build the recommender
            UserBasedRecommender recommender = new GenericUserBasedRecommender(model, neighborhood, similarity);

            // Step 5: Get recommendations for a specific user
            int userId = 5; // here i will type the id for which i want to check the siimilarity. i can change it and run it.
            List<RecommendedItem> recommendations = recommender.recommend(userId, 3);//means: Give top 3 recommendations

            // Step 6: Print results
            System.out.println("Recommendations for User " + userId + ":");
            for (RecommendedItem item : recommendations) {
                System.out.printf("Item %d, Score: %.2f\n", item.getItemID(), item.getValue());
            }

            

        } catch (IOException | TasteException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
