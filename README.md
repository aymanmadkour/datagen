# DataGen: Data Generation
Generating data is useful for testing. DataGen can be used to generate data such as random
names, emails, numeric values or dates, and can ensure uniqueness of values.

## Example
```
import java.util.Arrays;

import static com.aymanmadkour.datagen.DataGen.*;

public class Test {
    public static void main(String[] args) {
        RecordGen recordGen = RecordGen.builder()
                // Adding a named value "firstName", which can be used later
                // when generating email
                .add(named("firstName", concat(
                        string()
                                .length(1)
                                .characterClass(CharacterClass.UPPER_CASE)
                                .build(),
                        string()
                                .length(2)
                                .characterClass(CharacterClass.LOWER_CASE)
                                .build())))
                                
                // Adding a named value "lastName", which can be used later
                // when generating email
                .add(named("lastName", concat(
                        string()
                                .length(1)
                                .characterClass(CharacterClass.UPPER_CASE)
                                .build(),
                        string()
                                .length(2)
                                .characterClass(CharacterClass.LOWER_CASE)
                                .build())))
                                
                // Generate email from first and last names
                .add(named("email", concat(lowerCase(ref("firstName")), literal("."), lowerCase(ref("lastName")), literal("@domain.com"))))
                .add(integer(1000, 10000))
                .add(valueList("HR", "Engineering", "Sales", "Accounting"))
                .unique("email")    // Make sure email is unique
                .build();

        for (int i = 0; i < 1000; i++) {
            String[] record = recordGen.generate();
            System.out.println(Arrays.toString(record));
        }
    }
}
```