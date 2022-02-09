package student.adventure;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

public class AdventureGraingerLibraryDeserialization {
    private File file;
    private ObjectMapper mapper = new ObjectMapper();

    public AdventureGraingerLibraryDeserialization(File setFile) {
        file = setFile;
    }

    public void checkValidAdventureFile() {
        if (file == null) {
            throw new IllegalArgumentException();
        }
    }

    public Layout deserializeAdventureMap() throws IOException {
        Layout layout;

        try {
            layout = mapper.readValue(file, Layout.class);
        } catch (Exception io) {
            throw io;
        }

        return layout;
    }
}
