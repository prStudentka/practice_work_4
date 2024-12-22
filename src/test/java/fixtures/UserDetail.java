package fixtures;

import java.util.Map;

import static java.util.Map.entry;

public class UserDetail {

    public static Map<String,String> expected_id_2 =
        Map.ofEntries(
                entry("id", "2"),
                entry("name", "Janet"),
                entry("last_name", "Weaver"));


    public static Map<String,String> expected_id_7 =
        Map.ofEntries(
                entry("id", "7"),
                entry("name", "Michael"),
                entry("last_name", "Lawson"));

}
