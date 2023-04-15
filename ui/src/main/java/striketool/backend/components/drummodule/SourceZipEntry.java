package striketool.backend.components.drummodule;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;

import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

@AllArgsConstructor
public class SourceZipEntry implements SourceEntry {
    private ZipFile zipFile;
    private ZipEntry zipEntry;

    @Override
    public String getPath() {
        return zipEntry.getName();
    }

    @SneakyThrows
    public InputStream getInputStream() {
        return zipFile.getInputStream(zipEntry);
    }
}
