import React from 'react';
import { createNativeStackNavigator } from '@react-navigation/native-stack';

// page
import Home from '../screens/Home';
import ReservationDetails from '../screens/Reservation/ReservationDetails';

const ReservationStack = createNativeStackNavigator();

export default function ReservationNavigator( {route} ) {
    const { id, data } = route.params;

    return (
        <ReservationStack.Navigator> 
            {
                id === 1 ? (
                  <ReservationStack.Screen 
                      name="ReservationDetails"
                      component={ReservationDetails}
                      initialParams={{ data }}
                  />
              ) : <ReservationStack.Screen 
                        name="Home" 
                        component={Home} 
                        options={{
                            headerShown: false,
                        }}
                    />
            }
        </ReservationStack.Navigator>
    );
  }
  