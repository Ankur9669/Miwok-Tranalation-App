package com.example.translator;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class SimpleFragmentPagerAdaptor extends FragmentPagerAdapter
{

    private int noOfPages = 4;
    private String title[] = {"Numbers", "Phrases", "Family", "Colors"};

    public SimpleFragmentPagerAdaptor(@NonNull FragmentManager fm)
    {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position)
    {
        switch(position)
        {
            case 0:
                return new NumbersFragment();

                case 1:
                return new PhrasesFragment();

            case 2:
                 return new FamilyFragment();

            case 3:
                return new ColorsFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount()
    {
        return noOfPages;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position)
    {
        return title[position];
    }
}
