package com.example.ritasantiago.vetcare;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ritasantiago.vetcare.firebase.Animal;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by raquelramos on 04-03-2018.
 */

public class RVPetAdapter extends RecyclerView.Adapter<RVPetAdapter.AninalViewHolder>
{
    private List<Animal> animals = new ArrayList<>();

    @Override
    public AninalViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_pets, parent, false);
        AninalViewHolder pvh = new AninalViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(AninalViewHolder holder, int position) {
        holder.animalName.setText(animals.get(position).name);
        //holder.animalSex.setText(animals.get(position).sex);
        //holder.animalWeight.setText(animals.get(position).weight);
        //holder.animalSpecie.setText(animals.get(position).specie);
        holder.animalDateOfBirth.setText(animals.get(position).dateOfBirth);
        //holder.animalBreed.setText(animals.get(position).breed);
        //holder.animalCoat.setText(animals.get(position).coat);
        holder.animalOwner.setText(animals.get(position).owner_name);
        //holder.animalPhoto.setImageResource(animals.get(position).picture_id);
    }

    @Override
    public int getItemCount() {
        return animals.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public static class AninalViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView animalName;
        TextView animalSex;
        TextView animalWeight;
        TextView animalSpecie;
        TextView animalDateOfBirth;
        TextView animalBreed;
        TextView animalCoat;
        TextView animalOwner;

        ImageView animalPhoto;

        AninalViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cv);
            animalName = (TextView)itemView.findViewById(R.id.animal_name);
            //animalSex = (TextView) itemView.findViewById(R.id.animal_sex);
            //animalWeight = (TextView) itemView.findViewById(R.id.animal_weight);
            //animalSpecie = (TextView) itemView.findViewById(R.id.animal_specie);
            animalDateOfBirth = (TextView) itemView.findViewById(R.id.animal_date);
            //animalBreed = (TextView) itemView.findViewById(R.id.animal_breed);
            //animalCoat = (TextView) itemView.findViewById(R.id.animal_coat);
            animalOwner = (TextView) itemView.findViewById(R.id.animal_ownerName);
            //animalPhoto = (ImageView)itemView.findViewById(R.id.animal_photo);
        }
    }

    void addAll(List<Animal> animals) {
        this.animals.clear();
        this.animals = animals;
        notifyDataSetChanged();
    }

    void addAnimal(Animal animal) {
        this.animals.add(animal);
        notifyDataSetChanged();
    }
}