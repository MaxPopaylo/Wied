package ua.wied.presentation.screens.main.reports

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import ua.wied.domain.models.instruction.Element
import ua.wied.domain.models.folder.Folder
import ua.wied.domain.models.instruction.Instruction
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class ReportViewModel @Inject constructor(
) : ViewModel() {

    val list = listOf(
        Instruction(
            id = 1,
            title = "Inst 1",
            folderId = 101,
            posterUrl = "https://randomwordgenerator.com/img/picture-generator/5ee8d5444957b10ff3d8992cc12c30771037dbf85254794e732f7ad39349_640.jpg",
            elements = listOf(
                Element(
                    id = 1,
                    title = "Element 1",
                    videoUrl = "https://example.com/video1.mp4",
                    instructionId = 1,
                    orderNum = 1
                )
            ),
            createTime = LocalDateTime.now(),
            updateTime = LocalDateTime.now(),
            orderNum = 1
        ),
        Instruction(
            id = 2,
            title = "Inst 2",
            folderId = 102,
            posterUrl = "https://randomwordgenerator.com/img/picture-generator/5ee8d5444957b10ff3d8992cc12c30771037dbf85254794e732f7ad39349_640.jpg",
            elements = listOf(
                Element(
                    id = 2,
                    title = "Element 2",
                    videoUrl = "https://example.com/video2.mp4",
                    instructionId = 2,
                    orderNum = 1
                )
            ),
            createTime = LocalDateTime.now(),
            updateTime = LocalDateTime.now(),
            orderNum = 2
        ),
        Instruction(
            id = 3,
            title = "Inst 3",
            folderId = 103,
            posterUrl = "https://randomwordgenerator.com/img/picture-generator/5ee8d5444957b10ff3d8992cc12c30771037dbf85254794e732f7ad39349_640.jpg",
            elements = listOf(
                Element(
                    id = 3,
                    title = "Element 3",
                    videoUrl = "https://example.com/video3.mp4",
                    instructionId = 3,
                    orderNum = 1
                )
            ),
            createTime = LocalDateTime.now(),
            updateTime = LocalDateTime.now(),
            orderNum = 3
        )
    )

    private val folderList = listOf(
        Folder(1, "Folder 1", list),
        Folder(2, "Folder 2", list),
        Folder(3, "Folder 3", list),
        Folder(4, "Folder 4", list),
        Folder(5, "Folder 5", list)
    )

    private var _folders = MutableStateFlow<List<Folder<Instruction>>?>(null)
    val folders = _folders.asStateFlow()

    init {
        _folders.update { folderList }
    }


}