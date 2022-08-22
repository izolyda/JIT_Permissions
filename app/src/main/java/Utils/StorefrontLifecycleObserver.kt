import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.ActivityResultRegistry
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.example.permissionsexperiments.R

class StorefrontLifecycleObserver(
    private val registry: ActivityResultRegistry,
    private var context: Context
) : DefaultLifecycleObserver {
    lateinit var requestPermissionLauncher: ActivityResultLauncher<String>
    lateinit var requestMultiplePermissionsLauncher: ActivityResultLauncher<Array<String>>

    override fun onCreate(owner: LifecycleOwner) {
        requestPermissionLauncher =
            registry.register("key", owner, ActivityResultContracts.RequestPermission()) { granted: Boolean ->
                if (granted) {
                    Toast.makeText(context, R.string.permission_granted, Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, R.string.permission_denied, Toast.LENGTH_SHORT).show()
                }
            }

        requestMultiplePermissionsLauncher =
            registry.register("key", owner, ActivityResultContracts.RequestMultiplePermissions())
            {permissions ->
                permissions.entries.forEach {
                    if(it.value){
                        Toast.makeText(
                            context,
                            "${it.key.substringAfterLast(".")} permission granted.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    else{
                        Toast.makeText(
                            context,
                            "${it.key.substringAfterLast(".")} permission denied.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
            }
    }

    }


    fun requestPermission(permission: String) {
        requestPermissionLauncher.launch(permission)
    }

    fun requestMultiple(permissions: Array<String>) {
        requestMultiplePermissionsLauncher.launch(
            permissions
        )
    }

}