package rpl1pnp.fikri.githubuser.provider

import android.content.ContentProvider
import android.content.ContentUris
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import rpl1pnp.fikri.githubuser.repository.local.DatabaseBuilder
import rpl1pnp.fikri.githubuser.repository.local.DatabaseHelperImpl
import rpl1pnp.fikri.githubuser.utils.toUserFavorite

class UserFavoriteProvider : ContentProvider() {

    companion object {
        const val AUTHORITY = "rpl1pnp.fikri.githubuser.provider.UserFavoriteProvider"
        val CONTENT_URI = Uri.parse("content://$AUTHORITY/userFavorite")
        private const val USERFAVORITE = 1
        private const val USERFAVORITE_ID = 2
        private val uriMatcher = UriMatcher(UriMatcher.NO_MATCH)
        private lateinit var databaseHelper: DatabaseHelperImpl
        private const val DBNAME = "userFavorite"
    }

    init {
        uriMatcher.addURI(AUTHORITY, DBNAME, USERFAVORITE)
        uriMatcher.addURI(AUTHORITY, "$DBNAME/*", USERFAVORITE_ID)
    }

    override fun onCreate(): Boolean {
        return true
    }

    override fun query(
        uri: Uri, projection: Array<String>?, selection: String?,
        selectionArgs: Array<String>?, sortOrder: String?
    ): Cursor? {
        val code = uriMatcher.match(uri)
        return if (code == USERFAVORITE || code == USERFAVORITE_ID) {
            val context = context ?: return null
            databaseHelper = DatabaseHelperImpl(DatabaseBuilder.getInstance(context))
            val cursor: Cursor = if (code == USERFAVORITE) {
                databaseHelper.getAllUserProvider()
            } else {
                databaseHelper.getByIdProvider(ContentUris.parseId(uri).toInt())
            }
            cursor.setNotificationUri(context.contentResolver, uri)
            cursor
        } else {
            throw IllegalArgumentException("UNKNOWN URI: $uri")
        }
    }

    override fun getType(uri: Uri): String? {
        return null
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        val id: Long = when (USERFAVORITE) {
            uriMatcher.match(uri) -> values?.toUserFavorite()
                ?.let { databaseHelper.insertProvider(it) } ?: 0
            else -> 0
        }
        context?.contentResolver?.notifyChange(CONTENT_URI, null)

        return Uri.parse("$CONTENT_URI/$id")
    }

    override fun update(
        uri: Uri, values: ContentValues?, selection: String?,
        selectionArgs: Array<String>?
    ): Int {
        return 0
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        val deleted: Int = when (USERFAVORITE_ID) {
            uriMatcher.match(uri) -> databaseHelper.deleteProvider(uri.lastPathSegment?.toInt())
            else -> 0
        }
        context?.contentResolver?.notifyChange(CONTENT_URI, null)
        return deleted
    }
}