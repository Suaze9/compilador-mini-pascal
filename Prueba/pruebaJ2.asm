	.data
_a:	.word 0
_b:	.word 0
_c:	.word 0
_d:	.word 0
__msg0:	.asciiz "caso base\n"
__msg1:	.asciiz "\nrecursion"
__msg2:	.asciiz "\ntemp:"
__msg3:	.asciiz "antes de retornar: "
__msg4:	.asciiz "Ingrese un numero: "
__msg5:	.asciiz "El factorial es: "

	.text
	.globl main
_factorial_INT: 
	sw $fp, -4($sp)
	sw $ra, -8($sp)
	move $fp, $sp
	sub $sp, $sp, 12
	move $s0, $a0
	beq $s0, 1,etiq2
	b etiq3
etiq2:
	li $v0, 4
	la $a0, __msg0
	syscall
	li $t0, 1
	sw $t0, 0($sp)
	b etiq1
etiq3:
	li $v0, 4
	la $a0, __msg1
	syscall
	li $v0, 1
	move $a0, $s0
	syscall
	sub $t0, $s0, 1
	sw $t0, -4($sp)
	sw $t9, -8($sp)
	sw $s0, -12($sp)
	sw $s6, -16($sp)
	sw $s7, -20($sp)
	sub $sp, $sp, 20
	move $a0, $t0
	jal _factorial_INT
	add $sp, $sp, 20
	lw $s7, -20($sp)
	lw $s6, -16($sp)
	lw $s0, -12($sp)
	lw $t9, -8($sp)
	move $t0, $v0
	mul $t1, $s0, $t0
	sw $t1, 0($sp)
	li $v0, 4
	la $a0, __msg2
	syscall
	li $v0, 1
	lw $a0, 0($sp)
	syscall
etiq1:
	li $v0, 4
	la $a0, __msg3
	syscall
	li $v0, 1
	lw $a0, 0($sp)
	syscall
	lw $t0, 0($sp)
	move $v0, $t0
	b _factorial_INT_END

_factorial_INT_END:
	move $sp, $fp
	lw $ra, -8($sp)
	lw $fp, -4($sp)
	jr $ra

main:
	move $fp, $sp
	li $v0, 4
	la $a0, __msg4
	syscall
	li $v0, 5
	syscall
	sw $v0, _a
	sw $t9, -4($sp)
	sw $s6, -8($sp)
	sw $s7, -12($sp)
	sub $sp, $sp, 12
	lw $s7, _a
	move $a0, $s7
	jal _factorial_INT
	add $sp, $sp, 12
	lw $s7, -12($sp)
	lw $s6, -8($sp)
	lw $t9, -4($sp)
	move $t0, $v0
	sw $t0, _b
	li $v0, 4
	la $a0, __msg5
	syscall
	li $v0, 1
	lw $a0, _b
	syscall
etiq0:
	li $v0, 10
	syscall