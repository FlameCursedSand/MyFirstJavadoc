import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

public class downloadmuz {
    public static void main(String[] args) {
        String urlString = "https://cdn.uwupad.me/109782.mp3";

        //Указываем полный путь
        String downloadDir = "C:\\Users\\Acer\\Downloads";

        //Объединяем директорию и имя файла через системный разделитель
        String filePath = downloadDir + File.separator + "song56.mp3";

        Thread downloadThread = new Thread(() -> {
            System.out.println("Подождите, идет скачивание в: " + filePath);
            try {
                // Метод теперь возвращает время скачивания в миллисекундах
                long durationMs = downloadAudioFile(filePath, urlString);



                // Используем printf для красивого форматирования вывода (2 знака после запятой)
                System.out.printf("Скачивание успешно завершено за %d мс", durationMs);

            } catch (IOException ex) {
                System.err.println("Ошибка при скачивании: " + ex.getMessage());
            }
        });

        downloadThread.start();
        try {
            downloadThread.join();
        } catch (InterruptedException ex) {
            System.err.println("Скачивание было прервано");
            Thread.currentThread().interrupt(); //Восстанавливаем статус прерывания потока
        }
    }

    public static long downloadAudioFile(final String filePath, final String urlString) throws IOException {

        long startTime = System.currentTimeMillis();
        //проверяем существование папки перед записью (создаем если ее нет)
        File targetFile = new File(filePath);
        File parentDir = targetFile.getParentFile();
        if (parentDir != null && !parentDir.exists()) {
            parentDir.mkdirs(); //Создаст все недостающие папки в пути
        }

        //Try with resources потоки закроются автоматически даже если произойдет ошибка во время записи
        try (BufferedInputStream in = new BufferedInputStream(new URL(urlString).openStream());
             FileOutputStream fout = new FileOutputStream(targetFile)) {

            final byte[] data = new byte[1024]; //Можно увеличить до 8192 для большей скорости
            int count;
            while ((count = in.read(data, 0, 1024)) != -1) {
                fout.write(data, 0, count);
            }
        }
        long endTime = System.currentTimeMillis();
        return endTime - startTime;
    }
}