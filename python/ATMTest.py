import sys
import re
import datetime as dt
from os import system, name
import time

loginChances = 0
login = False
greeting = ""
usrname = ""
password = ""
history = []

class WithdrawHistory():
  def __init__(self, date, time, withdraw, previousAmount):
    self.date = date
    self.time = time
    self.withdraw = withdraw
    self.previousAmount = previousAmount

class ATMTest():
  def __init__(self, username, password, greeting, cash = 1000.00):
    self.username = username
    self.password = password
    self.greeting = greeting
    self.cash = cash
  
  def checkCash(self):
    print(f'Tu saldo actual es: ${self.cash} pesos.')
    while True:
      self.submenu()
      option = input("Digite una opcion: ")
      if int(option) == 1:
        break
      elif int(option) == 2:
        print("Saliste del cajero.")
        sys.exit()
  
  def withdraw(self):
    wdAmount = input("Cuanto deseas retirar: ")
    if self.withdrawIsValid(wdAmount):
      log = WithdrawHistory(dt.datetime.today().date(), dt.datetime.now().strftime("%H:%M:%S"), wdAmount, self.cash)
      history.append(log)
      currentCash = self.cash - float(wdAmount)
      print(f'Se retiro ${wdAmount} pesos en efectivo.')
      self.cash = currentCash
      while True:
        self.submenu()
        option = input("Digite una opcion: ")
        if int(option) == 1:
          break
        elif int(option) == 2:
          print("Saliste del cajero.")
          sys.exit()
    elif float(wdAmount) > self.cash:
      print("No hay fondos suficientes para retirar.")
      time.sleep(2)
    else:
      print("Cantidad erronea, debe introducir 2 decimales, no puede introducir cantidades negativas, letras o caracteres especiales.")
      time.sleep(2)
  
  def checkHistory(self):
    for log in history:
      print("--------------------")
      print(f'Fecha de retiro: {log.date}')
      print(f'Hora de retiro: {log.time}')
      print(f'Monto de retiro: {log.withdraw}')
      print(f'Monto anterior al retiro: {log.previousAmount}')
      print("--------------------")
    while True:
      self.submenu()
      option = input("Digite una opcion: ")
      if int(option) == 1:
        break
      elif int(option) == 2:
        print("Saliste del cajero.")
        sys.exit()
  
  def submenu(self):
    print("-------------------------------------------------------------------")
    print("| Que quieres hacer?                                         |")
    print("-------------------------------------------------------------------")
    print("| 1. Regresar al menu.                                            |")
    print("| 2. Salir del cajero.                                            |")
    print("-------------------------------------------------------------------")
  
  def menu(self):
    self.clear()
    print("-------------------------------------------------------------------")
    print(f'| {self.greeting} {self.username} bienvenido a cajeros automaticos ACME     |')
    print("-------------------------------------------------------------------")
    print("| 1. Consultar saldo.                                             |")
    print("| 2. Retirar saldo.                                               |")
    print("| 3. Historico de movimientos.                                    |")
    print("| 4. Salir.                                                       |")
    print("-------------------------------------------------------------------")
  
  def withdrawIsValid(self, withdraw):
    pattern = "^[0-9]*.[0-9]{2}$"
    return re.match(pattern, withdraw) and float(withdraw) <= self.cash and float(withdraw) >= 0.00
  
  def clear(self):
    if name == 'nt':
        _ = system('cls')
    else:
        _ = system('clear')

while loginChances < 3 and not(login):
  usrname = input("Introduce tu nombre: ")
  password = input("Introduce pin: ")

  def passIsValid(password):
    pattern = "^[0-9]*$"
    return re.match(pattern, password) and len(password) == 4 and int(password) == 1235

  if passIsValid(password):
    print("Entraste al cajero")
    if dt.datetime.now().hour < 12: greeting = "Buenos días"
    elif dt.datetime.now().hour >= 12 and dt.datetime.now().hour < 19: greeting = "Buenas tardes"
    else: greeting = "Buenas noches"
    login = True
  else:
    print("Password incorrecto!")
    if loginChances == 1:
      print("Te queda 1 intento")
    else:
      print(f'Te quedan {3 - (loginChances + 1)} intentos.')
    loginChances += 1

atm = ATMTest(usrname, password, greeting, 100000.00)

while login:
  atm.menu()
  option = input("Digite una de nuestras opciones: ")
  if int(option) == 1:
    atm.checkCash()
  elif int(option) == 2:
    atm.withdraw()
  elif int(option) == 3:
    atm.checkHistory()
  elif int(option) == 4:
    print("Saliste del cajero.")
    login = False
  else:
    print("No existe esa opcion en nuestro menu.")

print("Hasta luego.")
