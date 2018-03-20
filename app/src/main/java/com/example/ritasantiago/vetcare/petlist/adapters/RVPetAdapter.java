package com.example.ritasantiago.vetcare.petlist.adapters;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ritasantiago.vetcare.R;
import com.example.ritasantiago.vetcare.db.entity.Animal;
import com.example.ritasantiago.vetcare.interfaces.PetClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by raquelramos on 04-03-2018.
 */

public class RVPetAdapter extends RecyclerView.Adapter<RVPetAdapter.AnimalViewHolder>
{
    private List<Animal> animals = new ArrayList<>();
    private PetClickListener clickListener;

    public RVPetAdapter(PetClickListener clickListener){
        this.clickListener = clickListener;
    }

    @Override
    public AnimalViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_pets, parent, false);
        AnimalViewHolder pvh = new AnimalViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(AnimalViewHolder holder, final int position) {
        holder.animalName.setText(animals.get(position).name);
        holder.animalDateOfBirth.setText(animals.get(position).dateOfBirth);
        holder.animalOwner.setText(animals.get(position).owner_name);
        holder.animalPhoto.setImageBitmap(animals.get(position).image);
        holder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickListener.onPetClick(animals.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return animals.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public static class AnimalViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView animalName;
        TextView animalDateOfBirth;
        TextView animalOwner;
        ImageView animalPhoto;

        AnimalViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cv);
            animalName = (TextView)itemView.findViewById(R.id.g_name);
            animalDateOfBirth = (TextView) itemView.findViewById(R.id.animal_date);
            animalOwner = (TextView) itemView.findViewById(R.id.animal_ownerName);
            animalPhoto = (ImageView)itemView.findViewById(R.id.animal_photo);
        }
    }

    void addAll(List<Animal> animals) {
        this.animals.clear();
        this.animals = animals;
        notifyDataSetChanged();
    }

    public void addAnimal(Animal animal) {
        this.animals.add(animal);
        notifyDataSetChanged();
    }
}