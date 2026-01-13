package dev.mczs.cdsdataproxy.util;

import dev.mczs.cdsdataproxy.items.ConfigItem;
import dev.mczs.cdsdataproxy.items.TeamItem;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import static dev.mczs.cdsdataproxy.CdsDataProxyApplication.CONFIG;

public class FilesUtil {

    public static final Logger LOGGER = Logger.getLogger(FilesUtil.class.getName());

    public static ConfigItem readConfig() {
        File configFile = new File("config.yml");
        if (!configFile.exists()) {
            saveDefaultConfig(configFile);
        }

        ConfigItem config = null;
        try (InputStream input = new FileInputStream(configFile)) {
            Yaml yaml = new Yaml();
            config = yaml.loadAs(input, ConfigItem.class);
        } catch (Exception e) {
            LOGGER.severe("Config Error! Error = " + e.getMessage());
            System.exit(-1);
        }
        return config;
    }

    public static Map<Long, TeamItem> getTeamsMapFromExcel() {
        Map<Long, TeamItem> teamsMap = new HashMap<>();
        DataFormatter formatter = new DataFormatter();

        try (Workbook teamWorkBook = WorkbookFactory.create(new File(CONFIG.getTeamsFile()))) {
            Sheet teamSheet = teamWorkBook.getSheetAt(0);
            for (var row : teamSheet) {
                if (row.getRowNum() == 0) continue; // skip header

                Long teamId = Long.valueOf(formatter.formatCellValue(row.getCell(0)));
                String teamName = formatter.formatCellValue(row.getCell(1));
                int organizationId = Integer.parseInt(formatter.formatCellValue(row.getCell(2)));
                String organizationName = formatter.formatCellValue(row.getCell(3));
                String location = formatter.formatCellValue(row.getCell(4));
                int groupId = Integer.parseInt(formatter.formatCellValue(row.getCell(5)));
                String groupName = formatter.formatCellValue(row.getCell(6));

                TeamItem teamItem = new TeamItem(teamId, teamName, location, organizationId, organizationName, groupId, groupName);
                teamsMap.put(teamId, teamItem);
            }
        } catch (IOException e) {
            LOGGER.warning("Load Excel failed! Error = " + e.getMessage());
            LOGGER.info("Save team Excel template...");
            saveTeamExcel(new File(CONFIG.getTeamsFile()));
        }
        return teamsMap;
    }

    private static void saveTeamExcel(File file) {
        try (InputStream in = FilesUtil.class.getClassLoader().getResourceAsStream("teams.xlsx")) {
            saveFile(file, in);
        } catch (IOException e) {
            LOGGER.severe("Save Team Excel Error! Error = " + e.getMessage());
        }
    }

    private static void saveDefaultConfig(File configFile) {
        try (InputStream in = FilesUtil.class.getClassLoader().getResourceAsStream("config.yml")) {
            saveFile(configFile, in);
        } catch (IOException e) {
            LOGGER.severe("Save Default Config Error! Error = " + e.getMessage());
        }
    }

    private static void saveFile(File file, InputStream in) throws IOException {
        if (in == null) {
            LOGGER.severe("Resource not found inside JAR!");
            System.exit(-1);
        }

        try (OutputStream out = new FileOutputStream(file)) {
            byte[] buffer = new byte[1024];
            int len;
            while ((len = in.read(buffer)) != -1) {
                out.write(buffer, 0, len);
            }
            FilesUtil.LOGGER.info("Saved file successfully: " + file.getName());
        }
    }
}
