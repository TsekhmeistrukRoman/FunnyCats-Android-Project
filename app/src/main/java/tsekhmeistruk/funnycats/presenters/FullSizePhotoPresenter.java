package tsekhmeistruk.funnycats.presenters;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import tsekhmeistruk.funnycats.Constants;
import tsekhmeistruk.funnycats.activities.FullSizeImageActivity;
import tsekhmeistruk.funnycats.models.remote.ICatPhotosDataSource;
import tsekhmeistruk.funnycats.utils.rx.RxErrorAction;
import tsekhmeistruk.funnycats.utils.rx.RxRetryWithDelay;

/**
 * Created by Roman Tsekhmeistruk on 06.08.2017.
 */

public class FullSizePhotoPresenter extends BasePresenter<FullSizeImageActivity> {

    private final ICatPhotosDataSource catPhotosDataSource;

    public FullSizePhotoPresenter(ICatPhotosDataSource catPhotosDataSource) {
        this.catPhotosDataSource = catPhotosDataSource;
    }

    public void favourite(String imageId, String userId, String action) {
        FullSizeImageActivity view = getView();
        subscribe(catPhotosDataSource.favouritePhoto(imageId, userId, action)
                .retryWhen(new RxRetryWithDelay())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(t -> {
                    if (!Constants.ACTION_REMOVE.equals(action)) {
                        view.onAddToFavorites();
                    } else {
                        // TODO: implement logic when removed from favorites
                    }
                }, new RxErrorAction(view.getContext()))
        );
    }

}
