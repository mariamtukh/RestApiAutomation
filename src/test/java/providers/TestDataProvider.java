package providers;

import org.testng.annotations.DataProvider;

public class TestDataProvider {

    @DataProvider(name = "ageFilter")
    public Object[][] ageFilter() {
        return new Object[][] {
                {30, 200, "Alice"}
        };
    }

    @DataProvider(name = "genderFilter")
    public Object[][] genderFilter() {
        return new Object[][] {
                {"male", 200, "Bob"}
        };
    }

    @DataProvider(name = "invalidAge")
    public Object[][] invalidAge() {
        return new Object[][] { {-1, 400} };
    }

    @DataProvider(name = "invalidGender")
    public Object[][] invalidGender() {
        return new Object[][] { {"unknown", 422} };
    }
}