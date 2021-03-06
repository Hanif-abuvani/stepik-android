package org.stepic.droid.model.code

import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import org.stepic.droid.util.readMapCustomString
import org.stepic.droid.util.writeMapCustomString

data class CodeOptions(val limits: Map<String, CodeLimit>,
                       @SerializedName("execution_time_limit")
                       val executionTimeLimit: Int,
                       @SerializedName("code_templates")
                       val codeTemplates: HashMap<String, String>,
                       @SerializedName("execution_memory_limit")
                       val executionMemoryLimit: Int,
                       val samples: List<ParcelableStringList>
) : Parcelable {
    private constructor(parcel: Parcel) : this(
            parcel.readMapCustomString(CodeLimit::class.java.classLoader),
            parcel.readInt(),

            parcel.readBundle().let {
                val hashMap = it.getSerializable(CODE_TEMPLATES_KEY) as HashMap<String, String>
                hashMap
            },

            parcel.readInt(),
            mutableListOf<ParcelableStringList>().apply { parcel.readTypedList(this, ParcelableStringList.CREATOR) }
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeMapCustomString(limits, flags)
        parcel.writeInt(executionTimeLimit)

        val codeTemplatesBundle = Bundle().apply { putSerializable(CODE_TEMPLATES_KEY, codeTemplates) }
        parcel.writeBundle(codeTemplatesBundle)

        parcel.writeInt(executionMemoryLimit)
        parcel.writeTypedList(samples)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<CodeOptions> {
        override fun createFromParcel(parcel: Parcel): CodeOptions = CodeOptions(parcel)

        override fun newArray(size: Int): Array<CodeOptions?> = arrayOfNulls(size)

        private const val CODE_TEMPLATES_KEY = "CODE_TEMPLATES_KEY"
    }
}
