package ua.wied.domain.repository

import ua.wied.domain.models.FlowResult
import ua.wied.domain.models.UnitFlow
import ua.wied.domain.models.instruction.Instruction

interface InstructionRepository {
    suspend fun saveInstruction(
        title: String,
        posterUrl: String?,
        orderNum: Int,
        folderId: Int
    ): UnitFlow
    suspend fun updateInstruction(
        instructionId: Int,
        title: String,
        posterUrl: String?,
        orderNum: Int,
        folderId: Int
    ): UnitFlow
    suspend fun deleteInstruction(instructionId: Int): UnitFlow
    suspend fun getInstruction(instructionId: Int): FlowResult<Instruction>
    suspend fun toggleAccess(instructionId: Int, userId: Int): UnitFlow

    suspend fun saveElement(
        title: String,
        info: String,
        videoUrl: String?,
        orderNum: Int,
        instructionId: Int
    ): UnitFlow
    suspend fun updateElement(
        elementId: Int,
        title: String,
        info: String?,
        videoUrl: String?,
        orderNum: Int,
        instructionId: Int
    ): UnitFlow
    suspend fun deleteElement(elementId: Int, instructionId: Int): UnitFlow
    suspend fun reorderInstruction(instructionId: Int, folderId: Int, newOrder: Int): UnitFlow
}