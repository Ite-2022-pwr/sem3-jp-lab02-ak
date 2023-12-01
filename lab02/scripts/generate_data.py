#!/usr/bin/env python3

import random

PREFERENCES = "preferences.txt"
WYKAZ = "wykaz.txt"

ski_types = ("slalom", "carving", "gigant", "allmountain", "race")
age_groups = ("C", "J", "A")


def generate_preferences():
    """Generates preferences.txt file.
    format: 1,J,slalom:140;gigant:150
    """
    with open(PREFERENCES, "w") as file:
        for id in range(1, 51):
            age_group = random.choice(age_groups)
            skis = []
            for i in range(random.randint(1, 3)):
                ski_type = random.choice(ski_types)
                ski_length = random.randint(100, 200)
                skis.append(f"{ski_type}:{ski_length}")
            file.write(f"{id},{age_group},{';'.join(skis)}\n")


def generate_wykaz():
    """Generates wykaz.txt file.
    format: 3,slalom:130
    """
    with open(WYKAZ, "w") as file:
        for i in range(1, 21):
            quantity = random.randint(1, 5)
            ski_type = random.choice(ski_types)
            ski_length = random.randint(100, 200)
            file.write(f"{quantity},{ski_type}:{ski_length}\n")


if __name__ == "__main__":
    generate_preferences()
    generate_wykaz()
