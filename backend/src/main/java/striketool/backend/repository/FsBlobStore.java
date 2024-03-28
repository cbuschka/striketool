package striketool.backend.repository;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class FsBlobStore {

    private final File basedir;

    public FsBlobStore(File basedir) {
        this.basedir = basedir;
        this.basedir.mkdirs();
    }

    public void write(UUID id, byte[] data) {
        String path = toBlobPath(id);
        writeInternal(path, data);
    }

    public void write(String path, byte[] data) {
        path = toFilePath(path);
        writeInternal(path, data);
    }

    public byte[] read(UUID id) {
        String path = toBlobPath(id);
        return readInternal(path);
    }

    public byte[] read(String path) {
        path = toFilePath(path);
        return readInternal(path);
    }


    @SneakyThrows
    private void writeInternal(String path, byte[] data) {
        File file = new File(basedir, path);
        File tempFile = new File(file.getParentFile(), file.getName() + ".tmp" + System.nanoTime());
        tempFile.getParentFile().mkdirs();
        Files.write(tempFile.toPath(), data, StandardOpenOption.CREATE, StandardOpenOption.WRITE);
        Files.move(tempFile.toPath(), file.toPath(), StandardCopyOption.ATOMIC_MOVE, StandardCopyOption.REPLACE_EXISTING);
    }

    private String toFilePath(String path) {
        return "files" + "/" + path;
    }

    @SneakyThrows
    private byte[] readInternal(String path) {
        File file = new File(basedir, path);
        return Files.readAllBytes(file.toPath());
    }

    private String toBlobPath(UUID id) {
        String[] parts = toHexParts(id);
        String path = "blobs";
        for (String part : parts) {
            path = path + "/" + part;
        }
        return path;
    }

    private String[] toHexParts(UUID id) {

        byte[] bytes = UuidUtils.asBytes(id);
        List<String> parts = new ArrayList<>();
        StringBuilder buf = new StringBuilder(bytes.length * 2);
        for (int i = 0; i < bytes.length; ++i) {
            byte b = bytes[i];
            buf.append(Integer.toHexString(b));
            if (i % 4 == 0) {
                parts.add(buf.toString());
                buf = new StringBuilder();
            }
        }

        return parts.toArray(new String[0]);
    }

    public Iterable<Repository.Entry> list(String path) {
        path = toFilePath(path);
        File file = new File(basedir, path);
        List<Repository.Entry> entries = new ArrayList<>();
        File[] files = file.listFiles();
        if (files != null) {
            for (File curr : files) {
                entries.add(new FileEntry(curr));
            }
        }
        return entries;
    }

    @AllArgsConstructor
    private class FileEntry implements Repository.Entry {
        private final File file;

        @Override
        public String name() {
            return file.getName();
        }

        public int versions() {
            File[] versionFiles = file.listFiles();
            if (versionFiles == null) {
                return 0;
            }

            return versionFiles.length;
        }
    }
}
