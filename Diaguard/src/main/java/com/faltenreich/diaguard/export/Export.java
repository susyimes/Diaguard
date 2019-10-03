package com.faltenreich.diaguard.export;

import android.content.Context;
import android.net.Uri;

import com.faltenreich.diaguard.DiaguardApplication;
import com.faltenreich.diaguard.R;
import com.faltenreich.diaguard.data.PreferenceHelper;
import com.faltenreich.diaguard.data.entity.Measurement;
import com.faltenreich.diaguard.export.csv.CsvExport;
import com.faltenreich.diaguard.export.csv.CsvExportConfig;
import com.faltenreich.diaguard.export.csv.CsvImport;
import com.faltenreich.diaguard.export.pdf.PdfExport;
import com.faltenreich.diaguard.export.pdf.PdfExportConfig;
import com.faltenreich.diaguard.util.FileUtils;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import java.io.File;

public class Export {

    public static final String BACKUP_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    private static final String FILE_BACKUP_1_3_PREFIX = "diaguard_backup_";
    private static final String FILE_BACKUP_1_3_DATE_FORMAT = "yyyyMMddHHmmss";

    public enum Type {
        CSV,
        PDF;

        public String getExtension() {
            switch (this) {
                case CSV:
                    return "csv";
                case PDF:
                    return "pdf";
                default:
                    return null;
            }
        }
    }

    public static void exportPdf(
        Context context,
        ExportCallback callback,
        DateTime dateStart,
        DateTime dateEnd,
        Measurement.Category[] categories,
        PdfExportConfig.Style style
    ) {
        PdfExportConfig config = new PdfExportConfig(
            context,
            callback,
            style,
            dateStart,
            dateEnd,
            categories,
            PreferenceHelper.getInstance().exportNotes(),
            PreferenceHelper.getInstance().exportTags(),
            PreferenceHelper.getInstance().exportFood(),
            PreferenceHelper.getInstance().exportInsulinSplit()
        );
        PdfExport pdfExport = new PdfExport(config);
        pdfExport.execute();
    }

    public static void exportCsv(ExportCallback callback) {
        exportCsv(callback, null, null, null, true);
    }

    public static void exportCsv(
        ExportCallback callback,
        DateTime dateStart,
        DateTime dateEnd,
        Measurement.Category[] categories
    ) {
        exportCsv(callback, dateStart, dateEnd, categories, false);
    }

    private static void exportCsv(
        ExportCallback callback,
        DateTime dateStart,
        DateTime dateEnd,
        Measurement.Category[] categories,
        boolean isBackup
    ) {
        CsvExportConfig config = new CsvExportConfig(
            callback,
            dateStart,
            dateEnd,
            categories,
            isBackup
        );
        CsvExport csvExport = new CsvExport(config);
        csvExport.execute();
    }

    public static void importCsv(Context context, Uri uri, ExportCallback callback) {
        CsvImport csvImport = new CsvImport(context, uri);
        csvImport.setCallback(callback);
        csvImport.execute();
    }

    public static File getExportFile(Type type) {
        String fileName = String.format("%s%s%s_%s.%s",
            FileUtils.getPublicDirectory(),
            File.separator,
            DiaguardApplication.getContext().getString(R.string.app_name),
            DateTimeFormat.forPattern("yyyy-MM-dd_HH-mm").print(DateTime.now()),
            type.getExtension());
        return new File(fileName);
    }

    public static File getBackupFile(Type type) {
        String fileName = String.format("%s%s%s%s.%s",
            FileUtils.getPublicDirectory(),
            File.separator,
            FILE_BACKUP_1_3_PREFIX,
            DateTimeFormat.forPattern(FILE_BACKUP_1_3_DATE_FORMAT).print(DateTime.now()),
            type.getExtension());
        return new File(fileName);
    }
}
