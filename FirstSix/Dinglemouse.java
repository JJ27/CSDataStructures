package FirstSix;

import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;


/**
 * <img src="https://i.imgur.com/ta6gv1i.png?1" title="weekly coding challenge" />
 * <p>
 * <p>
 * *I bet you won't ever catch a Lift (a.k.a. elevator) again without thinking of this Kata !*
 *
 * <hr>
 * <p>
 * # Synopsis
 * <p>
 * A multi-floorLevel building has a Lift in it.
 * <p>
 * People are queued on different floors waiting for the Lift.
 * <p>
 * Some people want to go up. Some people want to go down.
 * <p>
 * The floorLevel they want to go to is represented by a number (i.e. when they enter the Lift this is the button they will press)
 * <p>
 * ```
 * BEFORE (people waiting in queues)               AFTER (people at their destinations)
 * +--+                                          +--+
 * /----------------|  |----------------\        /----------------|  |----------------\
 * 10|                |  | 1,4,3,2        |      10|             10 |  |                |
 * |----------------|  |----------------|        |----------------|  |----------------|
 * 9|                |  | 1,10,2         |       9|                |  |                |
 * |----------------|  |----------------|        |----------------|  |----------------|
 * 8|                |  |                |       8|                |  |                |
 * |----------------|  |----------------|        |----------------|  |----------------|
 * 7|                |  | 3,6,4,5,6      |       7|                |  |                |
 * |----------------|  |----------------|        |----------------|  |----------------|
 * 6|                |  |                |       6|          6,6,6 |  |                |
 * |----------------|  |----------------|        |----------------|  |----------------|
 * 5|                |  |                |       5|            5,5 |  |                |
 * |----------------|  |----------------|        |----------------|  |----------------|
 * 4|                |  | 0,0,0          |       4|          4,4,4 |  |                |
 * |----------------|  |----------------|        |----------------|  |----------------|
 * 3|                |  |                |       3|            3,3 |  |                |
 * |----------------|  |----------------|        |----------------|  |----------------|
 * 2|                |  | 4              |       2|          2,2,2 |  |                |
 * |----------------|  |----------------|        |----------------|  |----------------|
 * 1|                |  | 6,5,2          |       1|            1,1 |  |                |
 * |----------------|  |----------------|        |----------------|  |----------------|
 * G|                |  |                |       G|          0,0,0 |  |                |
 * |====================================|        |====================================|
 * ```
 * <hr>
 * # Rules
 * <p>
 * #### Lift Rules
 * <p>
 * * The Lift only goes up or down!
 * * Each floorLevel has both UP and DOWN Lift-call buttons (except top and ground floors which have only DOWN and UP respectively)
 * * The Lift never changes direction until there are no more people wanting to get on/off in the direction it is already travelling
 * * When empty the Lift tries to be smart. For example,
 * * If it was going up then it may continue up to collect the highest floorLevel person wanting to go down
 * * If it was going down then it may continue down to collect the lowest floorLevel person wanting to go up
 * * The Lift has a maximum capacity of people
 * * When called, the Lift will stop at a floorLevel **even if it is full**, although unless somebody gets off nobody else can get on!
 * * If the lift is empty, and no people are waiting, then it will return to the ground floorLevel
 * <p>
 * #### People Rules
 * <p>
 * * People are in **"queues"** that represent their order of arrival to wait for the Lift
 * * All people can press the UP/DOWN Lift-call buttons
 * * Only people going the **same direction** as the Lift may enter it, and they do so according to their **"queue" order**
 * * If a person is unable to enter a full Lift, they will press the UP/DOWN Lift-call button again after it has departed without them
 *
 *
 * <hr>
 * # Kata Task
 * <p>
 * * Get all the people to the floors they want to go to while obeying the **Lift rules** and the **People rules**
 * * Return a list of all floors that the Lift stopped at (in the order visited!)
 * <p>
 * NOTE: The Lift always starts on the ground floorLevel (and people waiting on the ground floorLevel may enter immediately)
 *
 * <hr>
 * # I/O
 * <p>
 * #### Input
 * <p>
 * * ```queues``` a list of queues of people for all floors of the building.
 * * The height of the building varies
 * * 0 = the ground floorLevel
 * * Not all floors have queues
 * * Queue index [0] is the "head" of the queue
 * * Numbers indicate which floorLevel the person wants go to
 * * ```capacity``` maximum number of people allowed in the lift
 * <p>
 * *Parameter validation* - All input parameters can be assumed OK. No need to check for things like:
 * <p>
 * * People wanting to go to floors that do not exist
 * * People wanting to take the Lift to the floorLevel they are already on
 * * Buildings with < 2 floors
 * * Basements
 * <p>
 * #### Output
 * <p>
 * * A list of all floors that the Lift stopped at (in the order visited!)
 *
 * <hr>
 * # Example
 * <p>
 * Refer to the example test cases.
 *
 * <hr>
 * Language Notes
 * <p>
 * * Python : The object will be initialized for you in the tests
 *
 * <hr>
 * <span style="color:red">
 * Good Luck -
 * <p>
 * DM
 *
 * </span>
 */
public class Dinglemouse {

    private Floor[] buildingFloors;
    private Lift lift;


    public Dinglemouse(Floor[] buildingFloors, int personCapacity) {
        this.buildingFloors = buildingFloors;
        this.lift = new Lift(buildingFloors[0], personCapacity);
    }


    public static int[] theLift(final int[][] queues, final int personCapacity) {
        System.out.printf("theLift with personCapacity=%d and queues: %s%n", //
                personCapacity, Arrays.deepToString(queues));
        if (queues == null || queues.length == 0) {
            return new int[0];
        }
        Floor[] buildingFloors = createFloorsWithPersonQueues(queues);
        Dinglemouse dinglemouse = new Dinglemouse(buildingFloors, personCapacity);
        dinglemouse.lift.operateLift();
        return dinglemouse.lift.stoppedFloors.stream().mapToInt(Floor::getFloorLevel).toArray();
    }

    private static Floor[] createFloorsWithPersonQueues(int[][] queues) {
        Floor[] buildingFloors = new Floor[queues.length];
        for (int floorLevel = 0; floorLevel < queues.length; floorLevel++) {
            Deque<Person> floorPersonQueue = Arrays.stream(queues[floorLevel])
                    .mapToObj(Person::new)
                    .collect(Collectors.toCollection(LinkedList::new));
            buildingFloors[floorLevel] = new Floor(floorLevel, floorPersonQueue);
        }
        return buildingFloors;
    }


    @Override
    public String toString() {
        String floorSeparationStr = StringUtils.repeat("-", 22);
        StringBuilder sb = new StringBuilder();
        sb.append("  /").append(floorSeparationStr).append("|   |");
        sb.append(floorSeparationStr).append("\\").append("\n");

        for (int floorLevel = buildingFloors.length - 1; floorLevel >= 0; floorLevel--) {
            sb.append(StringUtils.leftPad(String.valueOf(floorLevel), 2)).append("| ");
            Floor floor = buildingFloors[floorLevel];
            String personsAtDesinationFloorStr = floor.personsAtDesinationFloor()
                    .map(person -> String.valueOf(person.destinationFloor))
                    .collect(Collectors.joining(", "));
            sb.append(StringUtils.leftPad(personsAtDesinationFloorStr, 20));

            if (lift.currentFloor.floorLevel == floorLevel) {
                sb.append(" | X | ");
            } else {
                sb.append(" |   | ");
            }

            String personsGoingToOtherFloorStr = floor.personsGoingToOtherFloor()
                    .map(person -> String.valueOf(person.destinationFloor))
                    .collect(Collectors.joining(", "));
            sb.append(StringUtils.leftPad(personsGoingToOtherFloorStr, 20));
            sb.append(" |\n");
            sb.append("  |").append(floorSeparationStr).append("|   |").append(floorSeparationStr).append("|\n");
        }
        return sb.toString();
    }


    public class Lift {

        private Floor currentFloor;

        private boolean isGoingUpwards = true;
        private List<Floor> stoppedFloors;

        private int personCapazity;
        private List<Person> loadedPersons;


        public Lift(Floor startingFloor, int personCapazity) {
            this.currentFloor = startingFloor;
            this.personCapazity = personCapazity;
            this.loadedPersons = new LinkedList<>();
            this.stoppedFloors = new LinkedList<>();
            this.stoppedFloors.add(startingFloor);
        }


        /**
         * Operates this lift, which will transport all {@link Person}s to their destination {@link Floor}s
         * and return in the end back to the first {@link Floor}.
         */
        public void operateLift() {
            boolean hasMoved = false;
            System.out.println("                         +-a-+");
            System.out.println(Dinglemouse.this.toString());
            for (char step = 'b'; true; step++) {
                unloadPersons();
                loadPersons();

                Optional<Floor> nextFloor = firstFloorInTravelDirection();
                if (!nextFloor.isPresent()) {
                    nextFloor = lastFloorInDirectionWherePersonWantToEnter();
                    reverseLiftDirection();
                }

                if (nextFloor.isPresent()) {
                    moveLift(nextFloor.get());
                    hasMoved = true;
                } else if (!hasMoved) {
                    // 5. repeat 1 - 4 till this lift hasn't moved any more (no more persons want to ride)
                    break;
                } else {
                    hasMoved = false;
                }
                System.out.println("                         +-" + step + "-+");
                System.out.println(Dinglemouse.this.toString());
            }

            moveLift(buildingFloors[0]);
            System.out.println("finish with stoppedFloors: " + stoppedFloors);
        }


        /**
         * 1. unloads all {@link Person}s from the {@link Lift#loadedPersons},
         * which have as destination the {@link #currentFloor}.
         */
        private void unloadPersons() {
            for (int i = 0; i < loadedPersons.size(); i++) {
                Person person = loadedPersons.get(i);
                if (person.destinationFloor == currentFloor.floorLevel) {
                    loadedPersons.remove(i--);
                    currentFloor.personQueue.addLast(person);
                }
            }
            System.out.println("unloadPersons to Floor " + currentFloor + ": " + loadedPersons);
        }

        /**
         * 2. loads all {@link Person}s from {@link #currentFloor}, which want to ride in lifts direction.
         * Following {@link Floor#personQueue} in order and given {@link #personCapazity}.
         */
        private void loadPersons() {
            List<Person> personsEntering;
            if (isGoingUpwards) {
                personsEntering = currentFloor.personsGoingUpwards() //
                        .limit(getFreePersonCapazity()) //
                        .collect(Collectors.toList());
            } else {
                personsEntering = currentFloor.personsGoingDownwards() //
                        .limit(getFreePersonCapazity()) //
                        .collect(Collectors.toList());
            }
            personsEntering.forEach(person -> {
                currentFloor.personQueue.remove(person);
                loadedPersons.add(person);
            });
            System.out.println("loadPersons from Floor " + currentFloor + ": " + loadedPersons);
        }


        /**
         * @return 3.1 {@link Optional} first (nearest) {@link Floor} in lift travel direction,
         * where some {@link Person} want to enter OR exit.
         */
        private Optional<Floor> firstFloorInTravelDirection() {
            Optional<Floor> firstFloorPersonWantToEnter = firstFloorInDirectionWherePersonWantToEnter();
            Optional<Floor> firstFloorPersonWantToExit = firstFloorInDirectionWherePersonWantToExit();
            return Stream.of(firstFloorPersonWantToEnter, firstFloorPersonWantToExit)
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .min(Comparator.comparingInt(this::getFloorDistance));
        }

        /**
         * @return 3.1 a) {@link Optional} first (nearest) {@link Floor} in lift travel direction,
         * where some {@link Person} wants to enter.
         */
        private Optional<Floor> firstFloorInDirectionWherePersonWantToEnter() {
            return floorsInTravelDirection().filter(this::hasPersonWantToEnterInDirection).findFirst();
        }

        private boolean hasPersonWantToEnterInDirection(Floor floor) {
            if (isGoingUpwards) {
                return floor.personsGoingUpwards().findAny().isPresent();
            } else {
                return floor.personsGoingDownwards().findAny().isPresent();
            }
        }


        /**
         * @return 3.1 b) {@link Optional} first (nearest) {@link Floor}, where some {@link #loadedPersons} wants
         * to exit in lift travel direction
         */
        private Optional<Floor> firstFloorInDirectionWherePersonWantToExit() {
            return floorsInTravelDirection().filter(this::hasLoadedPersonWithDestination).findFirst();
        }

        private boolean hasLoadedPersonWithDestination(Floor floor) {
            return loadedPersons.stream()
                    .map(Person::getDestinationFloor)
                    .anyMatch(personDesinationFloorLevel -> floor.getFloorLevel() == personDesinationFloorLevel);
        }


        /**
         * @return 3.2 {@link Optional} last (fares) {@link Floor} in lift travel direction,
         * where some {@link Person} want to enter in any direction.
         */
        private Optional<Floor> lastFloorInDirectionWherePersonWantToEnter() {
            return floorsInTravelDirection() //
                    .filter(floor -> floor.personsGoingToOtherFloor().findAny().isPresent()) //
                    .max(Comparator.comparingInt(this::getFloorDistance)); //
        }


        /**
         * 4. moves the {@link Lift} to given destinationFloor and add it to {@link #stoppedFloors}
         *
         * @param destinationFloor next Floor, where this lift moves to and stops
         */
        private void moveLift(Floor destinationFloor) {
            if (!currentFloor.equals(destinationFloor)) {
                System.out.println("moveLift from Floor " + currentFloor + " to Floor " + destinationFloor);
                currentFloor = destinationFloor;
                stoppedFloors.add(destinationFloor);
            }
        }

        private void reverseLiftDirection() {
            isGoingUpwards = !isGoingUpwards;
        }


        private int getFreePersonCapazity() {
            return personCapazity - loadedPersons.size();
        }

        /**
         * @return A {@link Stream} containing upcoming {@link Floor}s in order for lift travel direction.
         */
        private Stream<Floor> floorsInTravelDirection() {
            if (isGoingUpwards) {
                return IntStream.range(currentFloor.floorLevel + 1, buildingFloors.length)
                        .mapToObj(floorLevel -> buildingFloors[floorLevel]);
            } else {
                return IntStream.rangeClosed(1, currentFloor.floorLevel)
                        .mapToObj(floorLevel -> buildingFloors[currentFloor.floorLevel - floorLevel]);
            }
        }

        private int getFloorDistance(Floor floor) {
            return Math.abs(currentFloor.floorLevel - floor.floorLevel);
        }

    }


    public static class Floor {

        private int floorLevel;
        private Deque<Person> personQueue;


        public Floor(int floorLevel, Deque<Person> personQueue) {
            this.floorLevel = floorLevel;
            this.personQueue = personQueue;
        }

        public Stream<Person> personsAtDesinationFloor() {
            return personQueue.stream().filter(person -> floorLevel == person.destinationFloor);
        }

        public Stream<Person> personsGoingToOtherFloor() {
            return personQueue.stream().filter(person -> floorLevel != person.destinationFloor);
        }

        public Stream<Person> personsGoingUpwards() {
            return personQueue.stream().filter(person -> floorLevel < person.destinationFloor);
        }

        public Stream<Person> personsGoingDownwards() {
            return personQueue.stream().filter(person -> floorLevel > person.destinationFloor);
        }

        public int getFloorLevel() {
            return floorLevel;
        }

        @Override
        public String toString() {
            return String.valueOf(floorLevel);
        }

    }


    public static class Person {

        int destinationFloor;

        public Person(int destinationFloor) {
            this.destinationFloor = destinationFloor;
        }


        public int getDestinationFloor() {
            return destinationFloor;
        }

        @Override
        public String toString() {
            return String.valueOf(destinationFloor);
        }

    }

}
