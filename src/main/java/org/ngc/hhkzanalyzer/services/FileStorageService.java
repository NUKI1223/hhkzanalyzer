package org.ngc.hhkzanalyzer.services;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.Normalizer;
import java.util.Locale;
import java.util.regex.Pattern;

@Service
public class FileStorageService {



    public FileStorageService() {

    }

    public String createFileName(MultipartFile file) throws IOException {
        // Получаем оригинальное имя файла
        String originalFilename = file.getOriginalFilename();

        // Преобразуем имя файла, заменяя русские символы на английские и удаляя специальные символы
        String cleanedFilename = cleanFileName(originalFilename);



        // Возвращаем имя файла для хранения в базе данных
        return cleanedFilename;
    }

    private String cleanFileName(String filename) {
        // Транслитерация русских символов на английские
        String transliterated = transliterate(filename);

        // Удаление всех символов, кроме букв, цифр, точек, подчеркиваний и дефисов
        return transliterated.replaceAll("[^a-zA-Z0-9._-]", "_");
    }

    private String transliterate(String text) {
        // Маппинг русских букв на английские (транслитерация)
        String[][] cyrillicToLatin = {
                {"А", "A"}, {"Б", "B"}, {"В", "V"}, {"Г", "G"}, {"Д", "D"}, {"Е", "E"}, {"Ё", "E"},
                {"Ж", "Zh"}, {"З", "Z"}, {"И", "I"}, {"Й", "Y"}, {"К", "K"}, {"Л", "L"}, {"М", "M"},
                {"Н", "N"}, {"О", "O"}, {"П", "P"}, {"Р", "R"}, {"С", "S"}, {"Т", "T"}, {"У", "U"},
                {"Ф", "F"}, {"Х", "Kh"}, {"Ц", "Ts"}, {"Ч", "Ch"}, {"Ш", "Sh"}, {"Щ", "Shch"}, {"Ы", "Y"},
                {"Э", "E"}, {"Ю", "Yu"}, {"Я", "Ya"}, {"а", "a"}, {"б", "b"}, {"в", "v"}, {"г", "g"},
                {"д", "d"}, {"е", "e"}, {"ё", "e"}, {"ж", "zh"}, {"з", "z"}, {"и", "i"}, {"й", "y"},
                {"к", "k"}, {"л", "l"}, {"м", "m"}, {"н", "n"}, {"о", "o"}, {"п", "p"}, {"р", "r"},
                {"с", "s"}, {"т", "t"}, {"у", "u"}, {"ф", "f"}, {"х", "kh"}, {"ц", "ts"}, {"ч", "ch"},
                {"ш", "sh"}, {"щ", "shch"}, {"ы", "y"}, {"э", "e"}, {"ю", "yu"}, {"я", "ya"}
        };

        // Выполняем замену русских символов на английские
        for (String[] mapping : cyrillicToLatin) {
            text = text.replace(mapping[0], mapping[1]);
        }

        return text;
    }
}
