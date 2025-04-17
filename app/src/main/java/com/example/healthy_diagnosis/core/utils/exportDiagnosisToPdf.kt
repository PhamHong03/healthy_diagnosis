import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import android.graphics.pdf.PdfDocument
import android.util.Log
import com.example.healthy_diagnosis.domain.usecases.result.DiagnosisFullInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.net.HttpURLConnection
import java.net.URL
import java.text.SimpleDateFormat
import java.util.*

suspend fun loadImageFromUrl(urlString: String): Bitmap? {
    return withContext(Dispatchers.IO) {
        try {
            val url = URL(urlString)
            val connection = url.openConnection() as HttpURLConnection
            connection.connect()
            val inputStream = connection.inputStream
            BitmapFactory.decodeStream(inputStream)
        } catch (e: Exception) {
            Log.e("ExportDiagnosisToPdf", "Error loading image from URL: $e")
            null
        }
    }
}

suspend fun exportDiagnosisToPdf(context: Context, detail: DiagnosisFullInfo): File {
    val document = PdfDocument()
    val pageInfo = PdfDocument.PageInfo.Builder(595, 842, 1).create()
    val page = document.startPage(pageInfo)
    val canvas: Canvas = page.canvas
    val paint = Paint()

    val pageWidth = pageInfo.pageWidth
    var y = 40f

    // ======= HEADER ========
    paint.textSize = 16f
    paint.isFakeBoldText = true
    paint.color = Color.parseColor("#1E90FF")
    paint.textAlign = Paint.Align.CENTER
    canvas.drawText("PHÒNG KHÁM ĐA KHOA H&D HEALTHY CHẨN ĐOÁN", pageWidth / 2f, y, paint)
    y += 20

    paint.textSize = 12f
    paint.color = Color.BLACK
    canvas.drawText("Đường 3/2, phường Xuân Khánh, quận Ninh Kiều, TP. Cần Thơ", pageWidth / 2f, y, paint)
    y += 40

    paint.textSize = 20f
    paint.isFakeBoldText = true
    paint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
    canvas.drawText("PHIẾU KẾT QUẢ CHẨN ĐOÁN", pageWidth / 2f, y, paint)
    y += 30

    val currentDate = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date())
    paint.textAlign = Paint.Align.RIGHT
    paint.textSize = 12f
    paint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.ITALIC)
    canvas.drawText("Cần Thơ, Ngày $currentDate", pageWidth - 40f, y, paint)
    paint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.NORMAL)

    y += 40
    paint.textAlign = Paint.Align.LEFT
    paint.textSize = 14f
    paint.isFakeBoldText = false
    paint.color = Color.BLACK

    val labelX = 40f
    val valueX = 240f

    fun drawRow(label: String, value: String?, isBoldLabel: Boolean = false) {
        paint.isFakeBoldText = isBoldLabel
        canvas.drawText(label, labelX, y, paint)
        paint.isFakeBoldText = false
        canvas.drawText(": ${value ?: "Không có"}", valueX, y, paint)
        y += 20
    }

    // ======= THÔNG TIN BỆNH NHÂN ========
    paint.isFakeBoldText = true
    paint.textSize = 15f
    canvas.drawText("I. THÔNG TIN BỆNH NHÂN", labelX, y, paint)
    y += 25
    paint.textSize = 14f
    paint.isFakeBoldText = false

    drawRow("Tên bệnh nhân", detail.patient_name, true)
    drawRow("Ngày sinh", detail.day_of_birth, true)
    drawRow("Giới tính", detail.gender, true)
    drawRow("Số điện thoại", detail.phone, true)

    // ======= THÔNG TIN CHẨN ĐOÁN ========
    y += 20
    paint.isFakeBoldText = true
    paint.textSize = 15f
    canvas.drawText("II. THÔNG TIN CHẨN ĐOÁN", labelX, y, paint)
    y += 25
    paint.textSize = 14f
    paint.isFakeBoldText = false

    drawRow("Loại bệnh", detail.category_name, true)
    drawRow("Tình trạng bệnh", detail.disease_name, true)

    // ======= KẾT LUẬN & HÌNH ẢNH ========
    y += 20
    paint.isFakeBoldText = true
    paint.textSize = 15f
    paint.color = Color.RED
    canvas.drawText("III. KẾT QUẢ CHẨN ĐOÁN", labelX, y, paint)
    y += 20

    paint.textSize = 12f
    paint.color = Color.BLACK
    canvas.drawText(
        "Kết luận => ${detail.disease_description?.uppercase(Locale.getDefault()) ?: "KHÔNG RÕ"}",
        labelX, y, paint
    )

    // ======= HÌNH ẢNH VÀ CHỮ "HÌNH ẢNH CHẨN ĐOÁN" ========
    val imageTopY = y + 30f
    val imageUrl = "http://192.168.1.9:5000/${detail.image_path}"

    val textLeftX = 40f
    val imageWidth = 300
    val imageRightMargin = 30f
    val imageRightX = pageWidth - imageWidth - imageRightMargin

    val bitmap = loadImageFromUrl(imageUrl)
    val imageSpacing = 30f

    bitmap?.let {
        val scaledBitmap = Bitmap.createScaledBitmap(it, imageWidth, 180, true)

        paint.color = Color.BLACK
        paint.textSize = 14f
        paint.isFakeBoldText = false
        paint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.ITALIC)
        canvas.drawText("Hình ảnh chẩn đoán", textLeftX, imageTopY, paint)
        paint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.NORMAL)

        val imageYPosition = imageTopY + imageSpacing

        canvas.drawBitmap(scaledBitmap, imageRightX, imageYPosition, null)
    } ?: run {
        paint.color = Color.RED
        canvas.drawText("Không thể tải ảnh", imageRightX, imageTopY, paint)
    }

    // ======= PHẦN XÁC NHẬN CUỐI TRANG ========
    val sectionY = imageTopY + 250f
    paint.color = Color.BLACK
    paint.textSize = 14f
    paint.isFakeBoldText = true
    canvas.drawText("Đa khoa H&D", labelX, sectionY, paint)
    canvas.drawText("Kết quả chẩn đoán trên hình ảnh siêu âm", labelX, sectionY + 20, paint)

    val rightX = pageWidth - 200f
    paint.isFakeBoldText = false
    canvas.drawText("Cần Thơ, Ngày $currentDate", rightX, sectionY, paint)
    canvas.drawText("Bác sĩ chẩn đoán", rightX, sectionY + 20, paint)
    paint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.ITALIC)
    paint.color = Color.RED
    canvas.drawText("(Ký và ghi rõ họ tên)", rightX, sectionY + 40, paint)
    paint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.NORMAL)

    document.finishPage(page)
    val file = File(context.getExternalFilesDir(null), "phieu_kham.pdf")
    document.writeTo(FileOutputStream(file))
    document.close()

    return file
}
