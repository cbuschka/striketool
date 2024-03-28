package striketool.backend.module;

import lombok.extern.slf4j.Slf4j;

import java.io.File;

@Slf4j
class FileUtils {
    static void deleteTree(File file) {
        if (file.isFile()) {
            log.debug("Deleting {}...", file);
            file.delete();
        } else if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (files != null) {
                for (File child : files) {
                    deleteTree(child);
                }
            }
            log.debug("Deleting {}...", file);
            file.delete();
        }
    }
}
