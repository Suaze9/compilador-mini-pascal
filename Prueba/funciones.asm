	.data
_a:	.word 0
_b:	.word 0
_c:	.word 0
_d:	.word 0
_bool1:	.word 0
_bool2:	.word 0
__msg0:	.asciiz "\nEl numero par: "
__msg1:	.asciiz "\nIngrese el numero base: "
__msg2:	.asciiz "\nIngrese el exponente: "
__msg3:	.asciiz "\n\nExponente Recursivo"
__msg4:	.asciiz "\n\nResp: "
__msg5:	.asciiz "\n\nExponente Iterativo"
__msg6:	.asciiz "\n"

	.text
	.globl main
_exponent_INT_x_INT: 
	sw $fp, -4($sp)
	sw $ra, -8($sp)
	move $s0, $a0
	move $s1, $a1
	move $fp, $sp
	sub $sp, $sp, 48
	move $t0, $s0
	sw $t0, 0($sp)
	li $t0, 1
	sw $t0, 4($sp)
etiq2:
	sub $t0, $s1, 1
	sw $s6, -4($sp)
	lw $s6, 4($sp)
	ble $s6, $t0,etiq3
	b etiq1
	lw $s6, -4($sp)
etiq3:
	sw $s6, -4($sp)
	lw $s6, 0($sp)
	mul $t0, $s6, $s0
	lw $s6, -4($sp)
	sw $t0, 0($sp)
etiq4:
	sw $s6, -4($sp)
	lw $s6, 4($sp)
	add $t0, $s6, 1
	lw $s6, -4($sp)
	sw $t0, 4($sp)
	b etiq2
etiq1:
	lw $t0, 0($sp)
	move $v0, $t0
	b _exponent_INT_x_INT_END

_exponent_INT_x_INT_END:
	move $sp, $fp
	lw $ra, -8($sp)
	lw $fp, -4($sp)
	jr $ra

_exponentrec_INT_x_INT: 
	sw $fp, -4($sp)
	sw $ra, -8($sp)
	move $s0, $a0
	move $s1, $a1
	move $fp, $sp
	sub $sp, $sp, 44
	beq $s1, 0,etiq6
	b etiq7
etiq6:
	li $t0, 1
	sw $t0, 0($sp)
	b etiq5
etiq7:
	sub $t0, $s1, 1
	sw $t0, -4($sp)
	sw $t9, -8($sp)
	sw $s0, -12($sp)
	sw $s1, -16($sp)
	sw $s6, -20($sp)
	sw $s7, -24($sp)
	sub $sp, $sp, 24
	move $a0, $s0
	move $a1, $t0
	jal _exponentrec_INT_x_INT
	add $sp, $sp, 24
	lw $s7, -24($sp)
	lw $s6, -20($sp)
	lw $s1, -16($sp)
	lw $s0, -12($sp)
	lw $t9, -8($sp)
	move $t0, $v0
	mul $t1, $s0, $t0
	sw $t1, 0($sp)
etiq5:
	lw $t0, 0($sp)
	move $v0, $t0
	b _exponentrec_INT_x_INT_END

_exponentrec_INT_x_INT_END:
	move $sp, $fp
	lw $ra, -8($sp)
	lw $fp, -4($sp)
	jr $ra

_numerospares_INT_x_INT: 
	sw $fp, -4($sp)
	sw $ra, -8($sp)
	move $s0, $a0
	move $s1, $a1
	move $fp, $sp
	sub $sp, $sp, 44
	move $t0, $s0
	sw $t0, 0($sp)
etiq9:
	sw $s6, -4($sp)
	lw $s6, 0($sp)
	ble $s6, $s1,etiq10
	b etiq8
	lw $s6, -4($sp)
etiq10:
	sw $s6, -4($sp)
	lw $s6, 0($sp)
	div $t0, $s6, 2
	lw $s6, -4($sp)
	sw $s6, -4($sp)
	lw $s6, 0($sp)
	sub $t1, $s6, 1
	lw $s6, -4($sp)
	div $t2, $t1, 2
	beq $t0, $t2,etiq12
	b etiq13
etiq13:
	li $v0, 4
	la $a0, __msg0
	syscall
	li $v0, 1
	lw $a0, 0($sp)
	syscall
etiq12:
etiq11:
	sw $s6, -4($sp)
	lw $s6, 0($sp)
	add $t0, $s6, 1
	lw $s6, -4($sp)
	sw $t0, 0($sp)
	b etiq9
etiq8:

_numerospares_INT_x_INT_END:
	move $sp, $fp
	lw $ra, -8($sp)
	lw $fp, -4($sp)
	jr $ra

main:
	move $fp, $sp
	li $v0, 4
	la $a0, __msg1
	syscall
	li $v0, 5
	syscall
	sw $v0, _a
	li $v0, 4
	la $a0, __msg2
	syscall
	li $v0, 5
	syscall
	sw $v0, _b
	li $v0, 4
	la $a0, __msg3
	syscall
	sw $t9, -4($sp)
	sw $s6, -8($sp)
	sw $s7, -12($sp)
	sub $sp, $sp, 12
	sw $s7, -16($sp)
	lw $s7, _a
	move $a0, $s7
	lw $s7, -16($sp)
	sw $s7, -16($sp)
	lw $s7, _b
	move $a1, $s7
	lw $s7, -16($sp)
	jal _exponentrec_INT_x_INT
	add $sp, $sp, 12
	lw $s7, -12($sp)
	lw $s6, -8($sp)
	lw $t9, -4($sp)
	move $t0, $v0
	sw $t0, _d
	li $v0, 4
	la $a0, __msg4
	syscall
	li $v0, 1
	lw $a0, _d
	syscall
	li $v0, 4
	la $a0, __msg5
	syscall
	sw $t9, -4($sp)
	sw $s6, -8($sp)
	sw $s7, -12($sp)
	sub $sp, $sp, 12
	sw $s7, -16($sp)
	lw $s7, _a
	move $a0, $s7
	lw $s7, -16($sp)
	sw $s7, -16($sp)
	lw $s7, _b
	move $a1, $s7
	lw $s7, -16($sp)
	jal _exponent_INT_x_INT
	add $sp, $sp, 12
	lw $s7, -12($sp)
	lw $s6, -8($sp)
	lw $t9, -4($sp)
	move $t0, $v0
	sw $t0, _d
	li $v0, 4
	la $a0, __msg4
	syscall
	li $v0, 1
	lw $a0, _d
	syscall
	li $v0, 4
	la $a0, __msg6
	syscall
	sw $t9, -4($sp)
	sw $s6, -8($sp)
	sw $s7, -12($sp)
	sub $sp, $sp, 12
	sw $s7, -16($sp)
	lw $s7, _a
	move $a0, $s7
	lw $s7, -16($sp)
	sw $s7, -16($sp)
	lw $s7, _b
	move $a1, $s7
	lw $s7, -16($sp)
	jal _numerospares_INT_x_INT
	add $sp, $sp, 12
	lw $s7, -12($sp)
	lw $s6, -8($sp)
	lw $t9, -4($sp)
	li $v0, 4
	la $a0, __msg6
	syscall
etiq0:
	li $v0, 10
	syscall