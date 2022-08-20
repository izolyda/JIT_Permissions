import android.content.Context
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
        requestMultiplePermissionsLauncher =
            registry.register("key", owner, ActivityResultContracts.RequestMultiplePermissions())
            { permissions ->
                permissions.forEach { actionMap ->
                    when (actionMap.key) {
                        android.Manifest.permission.ACCESS_COARSE_LOCATION -> {
                            if (actionMap.value) {
                                Toast.makeText(
                                    context,
                                    R.string.coarse_location_granted,
                                    Toast.LENGTH_SHORT
                                ).show()
                            } else {
                                Toast.makeText(
                                    context,
                                    R.string.coarse_location_denied,
                                    Toast.LENGTH_SHORT
                                ).show()
                            }

                        }
                        android.Manifest.permission.ACCESS_FINE_LOCATION -> {
                            if (actionMap.value) {
                                Toast.makeText(
                                    context,
                                    R.string.fine_location_granted,
                                    Toast.LENGTH_SHORT
                                ).show()
                            } else {

                                Toast.makeText(
                                    context,
                                    R.string.fine_location_denied,
                                    Toast.LENGTH_SHORT
                                ).show()
                            }

                        }
                    }
                }
            }

        requestPermissionLauncher =
            registry.register("key", owner, ActivityResultContracts.RequestPermission()) { granted: Boolean ->
                if (granted) {
                    Toast.makeText(context, "Permission Granted", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "Permission Denied", Toast.LENGTH_SHORT).show()
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