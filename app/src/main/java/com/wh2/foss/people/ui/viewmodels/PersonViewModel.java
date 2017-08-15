package com.wh2.foss.people.ui.viewmodels;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.BindingAdapter;
import android.databinding.ObservableField;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.wh2.foss.people.BR;
import com.wh2.foss.people.model.Person;
import com.wh2.foss.people.model.interactors.PersonsInteractor;
import com.wh2.foss.people.model.interactors.PersonsInteractorImpl;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.SingleTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class PersonViewModel extends BaseObservable {

    public final ObservableField<String> searchTerm = new ObservableField<>("");

    private static final String PHOTO_PREFIX = "photo";

    private Person person;

    private Bitmap photoTaken;
    private Bitmap signatureTaken;

    private PersonsInteractor personsInteractor;

    private static PersonViewModel personViewModel;

    public static PersonViewModel getInstance(Context context) {
        if (personViewModel == null) {
            personViewModel = new PersonViewModel(new Person(), context);
        }
        return personViewModel;
    }

    public PersonViewModel(Person person, Context context) {
        this.person = person;
        personsInteractor = new PersonsInteractorImpl(context);
        notifyPropertyChanged(BR._all);
    }

    public Observable<Person> performSearchPerson() {
        long numberForSearch = 0L;
        if (TextUtils.isDigitsOnly(searchTerm.get())) {
            numberForSearch = Long.parseLong(searchTerm.get());
        }
        return personsInteractor.searchByDocumentAndName(numberForSearch, searchTerm.get())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Single<String> performCreatePerson() {
        return performSavePhoto()
                .flatMap(photoPath -> {
                    setPhotoPath(photoPath);
                    return validate().equals("") ?
                            personsInteractor.createPerson(person).map(success -> success ? "Creada con exito" : "Error al actualizar") :
                            Single.just(PersonViewModel.this.validate());
                }).compose(applySchedulers());
    }

    public Single<String> performEditPerson() {
        return performSavePhoto()
                .flatMap(s -> {
                    setPhotoPath(s);
                    return validate().equals("") ?
                            personsInteractor.updatePerson(person).map(success -> success ? "Actualizado con exito" : "Error al actualizar") :
                            Single.just(PersonViewModel.this.validate());
                }).compose(applySchedulers());
    }

    public Single<Bitmap> performLoadPhoto() {
        return personsInteractor.loadImage(person.getPhotoPath()).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    public Single<String> performSavePhoto() {
        return personsInteractor.saveImage(PHOTO_PREFIX + person.getDocumentType() + person.getDocumentNumber(), photoTaken)
                .compose(applySchedulers());
    }

    private SingleTransformer<String, String> applySchedulers() {
        return observable -> observable.observeOn(Schedulers.io()).subscribeOn(AndroidSchedulers.mainThread());
    }

    /**
     * Validations
     **/

    public String checkEmpty(CharSequence charSequence) {
        if (TextUtils.isEmpty(charSequence)) return "Campo obligatorio";
        return "";
    }

    public String checkEmail(CharSequence charSequence) {
        if (!charSequence.toString().matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$"))
            return "Formato invalido";
        return "";
    }

    public String validate() {
        if (person.getDocumentType() == 0) return "Tipo de documento es Obligatorio";
        if (person.getDocumentNumber() == 0) return "N. Documento es obligatorio";
        if (TextUtils.isEmpty(person.getFirstName())) return "Ingrese Nombres";
        if (TextUtils.isEmpty(person.getLastName())) return "Ingrese Apellidos";
        if (TextUtils.isEmpty(person.getHomeAddress())) return "Ingrese Direccion de Residencia";
        if (TextUtils.isEmpty(person.getPhone())) return "Indique un Numero de Telefono o Celular";
        if (!TextUtils.isEmpty(person.getEmail()) && !person.getEmail().matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$"))
            return "Formato de Email Invalido";
        if (getPhotoTaken() == null) return "Foto es requerida.";
        return "";
    }

    /**
     * Data Accesors
     **/

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
        notifyPropertyChanged(BR._all);
    }

    /**
     * View Accesors
     **/

    @Bindable
    public int getDocumentType() {
        return person.getDocumentType();
    }

    public void setDocumentType(int documentType) {
        person.setDocumentType(documentType);
        notifyPropertyChanged(BR.documentType);
    }

    @Bindable
    public String getDocumentNumber() {
        return person.getDocumentNumber() == 0 ? "" : String.valueOf(person.getDocumentNumber());
    }

    public void setDocumentNumber(String documentNumber) {
        if (TextUtils.isEmpty(documentNumber)) {
            person.setDocumentNumber(0);
        } else {
            person.setDocumentNumber(Long.parseLong(documentNumber));
        }
        notifyPropertyChanged(BR.documentNumber);
    }

    @Bindable
    public String getFirstName() {
        return person.getFirstName();
    }

    public void setFirstName(String firstName) {
        person.setFirstName(firstName);
        notifyPropertyChanged(BR.firstName);
    }

    @Bindable
    public String getLastName() {
        return person.getLastName();
    }

    public void setLastName(String lastName) {
        person.setLastName(lastName);
        notifyPropertyChanged(BR.lastName);
    }

    @Bindable
    public String getHomeAddress() {
        return person.getHomeAddress();
    }

    public void setHomeAddress(String homeAddress) {
        person.setHomeAddress(homeAddress);
        notifyPropertyChanged(BR.homeAddress);
    }

    @Bindable
    public String getPhone() {
        return person.getPhone();
    }

    public void setPhone(String phone) {
        person.setPhone(phone);
        notifyPropertyChanged(BR.phone);
    }

    @Bindable
    public String getEmail() {
        return person.getEmail();
    }

    public void setEmail(String email) {
        person.setEmail(email);
        notifyPropertyChanged(BR.email);
    }

    @Bindable
    public String getPhotoPath() {
        return person.getPhotoPath();
    }

    public void setPhotoPath(String photoPath) {
        person.setPhotoPath(photoPath);
        notifyPropertyChanged(BR.photoPath);
    }

    public Bitmap getPhotoTaken() {
        return photoTaken;
    }

    public void setPhotoTaken(Bitmap photoTaken) {
        this.photoTaken = photoTaken;
    }

    public Bitmap getSignatureTaken() {
        return signatureTaken;
    }

    public void setSignatureTaken(Bitmap signatureTaken) {
        this.signatureTaken = signatureTaken;
    }

    @Bindable
    public String getFullName() {
        return person.getLastName() + " " + person.getFirstName();
    }

    @Bindable
    public String getDocumentTypeShort() {
        switch (getDocumentType()) {
            case 1:
                return "CC";
            case 2:
                return "TI";
            case 3:
                return "RC";
            case 4:
                return "PS";
            default:
                return "";
        }
    }

    @BindingAdapter({"app:imageUrl"})
    public static void loadImage(ImageView view, String url) {
        Glide.with(view.getContext()).load(url).into(view);
    }
}
