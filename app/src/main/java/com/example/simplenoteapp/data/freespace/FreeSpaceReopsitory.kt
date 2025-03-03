package com.example.simplenoteapp.data.freespace

import android.content.Context
import androidx.lifecycle.LiveData

class FreeSpaceRepository(private val freeSpaceDao: FreeSpaceDao) {
    val freeSpace: LiveData<FreeSpace?> = freeSpaceDao.getFreeSpace()

    suspend fun updateFreeSpace(header: String, detail: String) {
        val currentFreeSpace = freeSpaceDao.getFreeSpaceSync()

        if (currentFreeSpace == null) {
            // データが存在しない場合は、新しくデフォルトデータを挿入
            freeSpaceDao.insert(
                FreeSpace(
                    id = 1,
                    header = header,
                    detail = detail
                )
            )
        } else {
            // 既存データがある場合は更新
            freeSpaceDao.update(
                FreeSpace(
                    id = 1,
                    header = header,
                    detail = detail
                )
            )
        }
    }

    suspend fun initializeFreeSpace() {
        if (freeSpaceDao.getFreeSpaceSync() == null) {
            freeSpaceDao.insert(FreeSpace(id = 1, header = "見出しを入力", detail = "文字を入力"))
        }
    }
}