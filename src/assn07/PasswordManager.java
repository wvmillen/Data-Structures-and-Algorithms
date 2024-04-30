package assn07;

import java.util.*;

public class PasswordManager<K,V> implements Map<K,V> {
    private static final String MASTER_PASSWORD = "password321";
    private Account[] _passwords;

    public PasswordManager() {
        _passwords = new Account[50];
    }

    //helper function for hashing
    private int hashOn (K key) {
        int index = key.hashCode();
        index = Math.abs(index);

        return index % _passwords.length;
    }
    // TODO: put
    @Override
    public void put(K key, V value) {
        int index = hashOn(key); //hash the key

        Account<K, V> current = _passwords[index];
        if (current == null) {
            _passwords[index] = new Account<>(key, value);
            return;
        }

        while (current != null) {
            if (current.getWebsite().equals(key)) {
                current.setPassword(value);  // Update existing account.
                return;
            }
            if (current.getNext() == null) {
                current.setNext(new Account<>(key, value));  // Add new account.
                return;
            }
            current = current.getNext();
        }
    }


    // TODO: get
        @Override
        public V get(K key) {
            int index = Math.abs(key.hashCode()) % this._passwords.length;  //hash
            Account<K, V> previousAccount = this._passwords[index]; //// Retrieve the Account object at the calculated index.

            while (previousAccount != null) { //Iterate over the linked list at this index.
                if (previousAccount.getWebsite().equals(key)) { //if we found the account
                    return previousAccount.getPassword();
                }

                previousAccount = previousAccount.getNext();  //move to next account in the chain
            }

            return null; //if no account is found
        }

    // TODO: size

    @Override
    public int size() {
            int Size = 0; //intialize
            for (Account a: _passwords) { //iterate
                Account temp = a;
                if (temp != null) { //if current index is not empty
                    Size++;
                    while (temp.getNext() != null) { //traverse the linked list
                        Size++;
                        temp = temp.getNext(); //move to next account in the chain
                    }
                }
            }
            return Size;
    }

    // TODO: keySet
    @Override
    public Set<K> keySet() {
            Set<K> theKeySet = new HashSet<K>();  //new hashset to store the unique keys
            for (Account b: _passwords) { //iterate
                Account temp = b;
                if (temp != null) {
                    theKeySet.add((K) temp.getWebsite()); //for first account
                    while (temp.getNext() != null) {
                        temp = temp.getNext(); //move to next account
                        theKeySet.add((K) temp.getWebsite()); //add the website's key
                    }
                }
            }
            return theKeySet; //return the set of keys
            }

    // TODO: remove
    @Override

    public V remove(K key) {
            if (key == null) { //if key is null, nothing to be removed
                return null;
            }
            int removeIndex = Math.abs(key.hashCode()) % this._passwords.length; //get the index
            Account removeAccount = _passwords[removeIndex]; //get the account of that index
            while (removeAccount != null) { //iterate
                if (removeAccount.getWebsite() == key) { //if account matches the key, remove it
                    Account tempo = removeAccount;
                    if (removeAccount == _passwords[removeIndex]) { //update head if head is removed
                        _passwords[removeIndex] = removeAccount.getNext();
                        removeAccount = null;
                        return (V) tempo.getPassword();
                    } else {
                        Account the_fAccount = _passwords[removeIndex];
                        while (the_fAccount.getNext() != null) {
                            if (the_fAccount.getNext() == removeAccount) { //go through list
                                the_fAccount.setNext(removeAccount.getNext());
                                break;
                            }
                            the_fAccount = the_fAccount.getNext();
                        }
                    }
                    removeAccount = null;
                    return (V) tempo.getPassword(); //return the password of the removed account

                }
                removeAccount = removeAccount.getNext();
            }
            return null; //if key is not found in the list
    }

    // TODO: checkDuplicate
    @Override
    public List<K> checkDuplicate(V value) {
            List<K> duplicateList = new ArrayList<>(); //create arraylist to store keys of duplicates

            for (int i = 0; i < this._passwords.length; i++) { //iterate
                Account<K, V> acnt = this._passwords[i]; //start with head

                while (acnt != null) {
                    if (acnt.getPassword().equals(value)) { //if the account matches the value
                        duplicateList.add(acnt.getWebsite()); //add the key (website) to the list of duplicates
                    }
                    acnt = acnt.getNext(); //go to the nest account
                }
            }

            return duplicateList; //return the duplicates
    }

    // TODO: checkMasterPassword
    @Override
    public boolean checkMasterPassword(String enteredPassword) {
            return enteredPassword.equals(MASTER_PASSWORD);
    }

    @Override
    public String generatesafeRandomPassword(int length) {
        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = length;
        Random random = new Random();

        // TODO: Ensure the minimum length is 4


        String generatedString = random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();

        return generatedString;
    }

    /*
    Used for testing, do not change
     */
    public Account[] getPasswords() {
        return _passwords;
    }
}
