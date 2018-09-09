package com.squareup.picasso;

import android.annotation.TargetApi;
import android.content.ContentResolver;
import android.content.Context;
import android.content.UriMatcher;
import android.net.Uri;
import android.os.Build.VERSION;
import android.provider.ContactsContract.Contacts;
import com.squareup.picasso.Picasso.LoadedFrom;
import com.squareup.picasso.RequestHandler.Result;
import java.io.IOException;
import java.io.InputStream;

class ContactsPhotoRequestHandler extends RequestHandler {
    private static final int ID_CONTACT = 3;
    private static final int ID_DISPLAY_PHOTO = 4;
    private static final int ID_LOOKUP = 1;
    private static final int ID_THUMBNAIL = 2;
    private static final UriMatcher matcher = new UriMatcher(-1);
    private final Context context;

    @TargetApi(14)
    private static class ContactPhotoStreamIcs {
        private ContactPhotoStreamIcs() {
        }

        static InputStream get(ContentResolver contentResolver, Uri uri) {
            return Contacts.openContactPhotoInputStream(contentResolver, uri, true);
        }
    }

    static {
        matcher.addURI("com.android.contacts", "contacts/lookup/*/#", ID_LOOKUP);
        matcher.addURI("com.android.contacts", "contacts/lookup/*", ID_LOOKUP);
        matcher.addURI("com.android.contacts", "contacts/#/photo", ID_THUMBNAIL);
        matcher.addURI("com.android.contacts", "contacts/#", ID_CONTACT);
        matcher.addURI("com.android.contacts", "display_photo/#", ID_DISPLAY_PHOTO);
    }

    ContactsPhotoRequestHandler(Context context) {
        this.context = context;
    }

    public boolean canHandleRequest(Request data) {
        Uri uri = data.uri;
        return "content".equals(uri.getScheme()) && Contacts.CONTENT_URI.getHost().equals(uri.getHost()) && matcher.match(data.uri) != -1;
    }

    public Result load(Request request, int networkPolicy) throws IOException {
        InputStream is = getInputStream(request);
        return is != null ? new Result(is, LoadedFrom.DISK) : null;
    }

    private InputStream getInputStream(Request data) throws IOException {
        ContentResolver contentResolver = this.context.getContentResolver();
        Uri uri = data.uri;
        switch (matcher.match(uri)) {
            case ID_LOOKUP /*1*/:
                uri = Contacts.lookupContact(contentResolver, uri);
                if (uri == null) {
                    return null;
                }
                break;
            case ID_THUMBNAIL /*2*/:
            case ID_DISPLAY_PHOTO /*4*/:
                return contentResolver.openInputStream(uri);
            case ID_CONTACT /*3*/:
                break;
            default:
                throw new IllegalStateException("Invalid uri: " + uri);
        }
        if (VERSION.SDK_INT < 14) {
            return Contacts.openContactPhotoInputStream(contentResolver, uri);
        }
        return ContactPhotoStreamIcs.get(contentResolver, uri);
    }
}
