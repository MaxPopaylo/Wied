package ua.wied.presentation.screens.main.reports

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import ua.wied.domain.models.instruction.Element
import ua.wied.domain.models.instruction.Folder
import ua.wied.domain.models.instruction.Instruction
import javax.inject.Inject

@HiltViewModel
class ReportViewModel @Inject constructor(
) : ViewModel() {

    private val list = listOf(
        Instruction(1, "Inst 1", "https://randomwordgenerator.com/img/picture-generator/5ee8d5444957b10ff3d8992cc12c30771037dbf85254794e732f7ad39349_640.jpg", listOf(
            Element("")
        )),
        Instruction(2, "Inst 2", "https://randomwordgenerator.com/img/picture-generator/5ee8d5444957b10ff3d8992cc12c30771037dbf85254794e732f7ad39349_640.jpg", listOf(
            Element("")
        )),
        Instruction(3, "Inst 3", "https://randomwordgenerator.com/img/picture-generator/5ee8d5444957b10ff3d8992cc12c30771037dbf85254794e732f7ad39349_640.jpg", listOf(
            Element("")
        )),
    )
    private val folderList = listOf(
        Folder(1, "Folder 1", list),
        Folder(2, "Folder 2", list),
        Folder(3, "Folder 3", list),
        Folder(4, "Folder 4", list),
        Folder(5, "Folder 5", list)
    )

    private var _folders = MutableStateFlow<List<Folder<Instruction>>>(folderList)
    val folders = _folders.asStateFlow()


}